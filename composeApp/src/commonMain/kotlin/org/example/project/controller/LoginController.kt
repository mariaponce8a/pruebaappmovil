package app_firma_digital.org.example.project.controller

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.repository.SeguridadRepository
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.core.Validation.ValidationResult
import org.example.project.core.Validation.Validators
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import org.example.project.model.general.ErrorBase
import org.example.project.model.general.RespuestaGeneral
import org.example.project.model.login.LoginPayload
import org.example.project.model.login.UsuarioAutenticado
import org.example.project.view.seguridad.login.LoginAction
import org.example.project.view.seguridad.login.LoginState

class LoginController(
    private val repository: SeguridadRepository
) : ViewModel() {


    // Estado inmutable que observa la vista
    private val _state = MutableStateFlow(_root_ide_package_.org.example.project.view.seguridad.login.LoginState())
    val state = _state.asStateFlow()

    // Nuevo: eventos one-shot para la UI (snackbar, navegacion, etc.)
    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events: SharedFlow<String> = _events.asSharedFlow()

    // --- VALIDACIONES ---
    private fun validateUsuario(usuario: String): ValidationResult = Validators.required(usuario)

    private fun validateClave(clave: String): ValidationResult = Validators.required(clave)

    // --- EVENTOS DESDE LA UI ---
    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.onUserValueChange -> updateUsuario(action.usuario)
            is LoginAction.onPasswordValueChange -> updateClave(action.clave)
            is LoginAction.OnLoginFormSubmit -> onSubmit()
            else -> {}
        }
    }

    // --- LÓGICA DE ACTUALIZACIÓN DE CAMPOS ---
    private fun updateUsuario(usuario: String) {
        val result = validateUsuario(usuario)
        _state.update {
            it.copy(
                usuario = usuario,
                usuarioError = (result as? ValidationResult.Invalid)?.reason ?: ""
            )
        }
        validateForm()
    }

    private fun updateClave(clave: String) {
        val result = validateClave(clave)
        _state.update {
            it.copy(
                clave = clave,
                claveError = (result as? ValidationResult.Invalid)?.reason ?: ""
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val validUsuario = validateUsuario(state.value.usuario) is ValidationResult.Valid
        val validClave = validateClave(state.value.clave) is ValidationResult.Valid
        _state.update { it.copy(isButtonEnabled = validUsuario && validClave) }
    }

    // --- ACCIÓN DE LOGIN ---
    fun onSubmit() {
        val usuarioValid = validateUsuario(state.value.usuario)
        val claveValid = validateClave(state.value.clave)
        if (usuarioValid is ValidationResult.Valid && claveValid is ValidationResult.Valid) {
            val payload = LoginPayload(
                usuario = state.value.usuario,
                clave = state.value.clave,
                mac = "",
                ip = "",
                so = ""
            )
            executeLogin(payload)
        }
    }

    // --- LLAMADO AL REPOSITORIO (mejor manejo de errores) ---
    private fun executeLogin(payload: LoginPayload) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, isButtonEnabled = false) }

        try {
            val result = repository.login(payload)
            println("respuesta controller => $result")
            result
                .onSuccess { controlarRespuestaLogin(it) }
                .onFailure { throwable ->
                    // Actualiza estado y emite evento
                    val mensaje = throwable?.message ?: "apierr"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isButtonEnabled = true,
                            // opcional: mantiene errorGeneral si quieres
                            errorGeneral = mensaje
                        )
                    }
                    // Emitir evento para snackbar (one-shot)
                    _events.tryEmit(mensaje) // tryEmit evita suspender si no hay collector
                }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    isButtonEnabled = true,
                    errorGeneral = "apierr"
                )
            }
            _events.tryEmit("apierr")
        }
    }

    // --- RESPUESTA EXITOSA ---
    private fun controlarRespuestaLogin(jsonElement: JsonElement) {
        val respuesta = Json.decodeFromJsonElement<RespuestaGeneral>(normalizeJson(jsonElement))
        try {
            println(" idRespuesta => $respuesta")
            if (respuesta.idrespuesta == 1) {

                val usuarioAutenticado = Json.decodeFromJsonElement<UsuarioAutenticado>(respuesta.mensaje)

                // Éxito
                _state.update {
                    it.copy(
                        isLoading = false,
                        usuario = "",
                        clave = "",
                        usuarioError = "",
                        claveError = "",
                        isButtonEnabled = false,
                        errorGeneral = null,
                        esAutenticacionOtp = usuarioAutenticado.dfaHabilitado,
                        esCambioClave = usuarioAutenticado.cambioClaveHabilitado,
                        esAutenticacionNormal = !usuarioAutenticado.dfaHabilitado,
                        autenticacionCorrecta = if (respuesta.idrespuesta == 1) true else false
                    )
                }
            } else {
                // Error con código desde backend
                val respuestaError = Json.decodeFromJsonElement<ErrorBase>(respuesta.mensaje)

                println("Codigo $respuestaError")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorGeneral = respuestaError.codigo,
                        isButtonEnabled = true
                    )
                }
            }
        } catch (e: Exception) {
            setearErrorGeneral()
        }
    }

    // --- MANEJO DE ERROR ---
    private fun setearErrorGeneral() {
        _state.update {
            it.copy(
                isLoading = false,
                errorGeneral = "apierr",
                isButtonEnabled = true
            )
        }
    }

    // --- FUNCIONES AUXILIARES ---
    private fun normalizeJson(jsonElement: JsonElement): JsonObject {
        return when (jsonElement) {
            is JsonPrimitive -> Json.parseToJsonElement(jsonElement.content).jsonObject
            is JsonObject -> jsonElement
            else -> JsonObject(emptyMap())
        }
    }

    private fun extractCodigo(mensajeElement: JsonElement): String {
        return when (mensajeElement) {
            is JsonObject -> mensajeElement["codigo"]?.jsonPrimitive?.contentOrNull ?: "apierr"
            is JsonPrimitive -> mensajeElement.contentOrNull ?: "apierr"
            else -> "err"
        }
    }
}