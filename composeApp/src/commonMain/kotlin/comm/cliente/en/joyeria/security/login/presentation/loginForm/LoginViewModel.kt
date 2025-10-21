package comm.cliente.en.joyeria.security.login.presentation.loginForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comm.cliente.en.joyeria.core.domain.onError
import comm.cliente.en.joyeria.core.domain.onSuccess
import comm.cliente.en.joyeria.core.presentation.toUiText
import comm.cliente.en.joyeria.security.login.domain.LoginPayload
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticadoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val UsuarioAutenticadoRepository: UsuarioAutenticadoRepository) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    private val loginJob: Job? = null;
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginFormSubmit -> {
                _state.update {
                    it.copy(
                        usuario = action.usuario,
                        clave = action.clave,
                        so = action.so,
                        mac = action.mac,
                        direccionIp = action.ip
                    )
                }

                val body = LoginPayload(
                    usuario = action.usuario,
                    clave = action.clave,
                    so = action.so,
                    mac = action.mac,
                    ip = action.ip
                )
                executeLogin(body)
            }

            is LoginAction.onUserValueChange -> {
                _state.update { it.copy(usuario = action.usuario) }
            }

            is LoginAction.onPasswordValueChange -> {
                _state.update { it.copy(clave = action.clave) }
            }

            is LoginAction.hidePassword -> {
                _state.update { it.copy(clave = action.clave) }
            }

            LoginAction.ExecuteLogin -> {
                val body =  LoginPayload(
                    usuario = state.value.usuario ,
                    clave = state.value.clave,
                    so = state.value.so,
                    mac = state.value.mac,
                    ip = state.value.direccionIp
                )
                executeLogin(body)
            }
        }
    }

    private fun observeUsuario() {
        state
            .map { it.usuario }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { usuario ->
                when {
                    usuario.isBlank() -> {
                        _state.update {
                            it.copy(
                                usuario = usuario.trim()
                            )
                        }
                    }
                }
            }
    }

    private fun observeClave() {
        state
            .map { it.clave }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { pass ->
                when {
                    pass.isBlank() -> {
                        _state.update {
                            it.copy(
                                usuario = pass.trim()
                            )
                        }
                    }
                }
            }
    }

     fun executeLogin(body: LoginPayload) = viewModelScope.launch {
        println("executeLogin llamado con body: $body")

        _state.update {
            it.copy(isLoading = true, isButtonEnabled = false)
        }

        UsuarioAutenticadoRepository
            .login(body)
            .onSuccess { value ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        clave = "",
                        usuario = "",
                        direccionIp = "",
                        mac = "",
                        so = "",
                        claveError = "",
                        usuarioError = "",
                        isButtonEnabled = false
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        claveError = error.toUiText().toString(),
                        isButtonEnabled = true
                    )
                }
            }
    }
}