package comm.cliente.en.joyeria.security.login.presentation.loginForm

data class LoginState(
    //campos de formulario
    val usuario: String = "",
    val clave: String = "",
    val mac: String = "",
    val so: String = "",
    val direccionIp: String = "",
    //estados
    val usuarioError: String? = null,
    val claveError: String? = null,
    val isLoading: Boolean = false,
    val isButtonEnabled: Boolean = false
)
