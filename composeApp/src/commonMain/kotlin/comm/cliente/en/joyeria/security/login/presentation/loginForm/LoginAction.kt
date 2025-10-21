package comm.cliente.en.joyeria.security.login.presentation.loginForm


sealed interface LoginAction {
    data class OnLoginFormSubmit(
        val usuario: String,
        val clave: String,
        val mac: String,
        val so: String,
        val ip: String
    ) : LoginAction

    object ExecuteLogin : LoginAction

    data class onUserValueChange(val usuario: String) : LoginAction
    data class onPasswordValueChange(val clave: String) : LoginAction
    data class hidePassword(val clave: String) : LoginAction
}