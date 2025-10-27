package controller

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import repository.SeguridadRepository
import androidx.lifecycle.viewModelScope
import core.Validation.ValidationResult
import core.Validation.Validators
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import model.login.LoginPayload
import view.Seguridad.LoginAction
import view.Seguridad.LoginState

class SeguridadController(
    private val repository: SeguridadRepository
) : ViewModel() {

    // Estado inmutable que observa la vista
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

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
            // Si repository.login devuelve Result<JsonElement>
            val result = repository.login(payload)

            // Manejo si es Kotlin Result
            if (result is Result<*>) {
                @Suppress("UNCHECKED_CAST")
                (result as Result<JsonElement>)
                    .onSuccess { controlarRespuestaLogin(it) }
                    .onFailure { throwable ->
                        println("Login failure from repository: ${throwable.message}")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = throwable?.message ?: "apierr",
                                isButtonEnabled = true
                            )
                        }
                    }
                return@launch
            }

            // Si repository.login devuelve directamente JsonElement (no Result)
            if (result is JsonElement) {
                controlarRespuestaLogin(result)
                return@launch
            }

            // Si llega algo inesperado
            println("Login: respuesta inesperada del repositorio: ${result?.javaClass}")
            setearErrorGeneral()
        } catch (e: Exception) {
            // Cualquier excepción de red/parsing/etc
            println("Exception en executeLogin: ${e.message}")
            setearErrorGeneral()
        }
    }

    // --- RESPUESTA EXITOSA ---
    private fun controlarRespuestaLogin(jsonElement: JsonElement) {
        try {
            val parsed = normalizeJson(jsonElement)
            val idRespuesta = parsed["idrespuesta"]?.jsonPrimitive?.intOrNull ?: -1
            val mensajeElement = parsed["mensaje"] ?: return

            if (idRespuesta == 1) {
                // Éxito
                _state.update {
                    it.copy(
                        isLoading = false,
                        usuario = "",
                        clave = "",
                        usuarioError = "",
                        claveError = "",
                        isButtonEnabled = false,
                        errorGeneral = null
                    )
                }
            } else {
                // Error con código desde backend
                val codigo = extractCodigo(mensajeElement)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorGeneral = codigo,
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