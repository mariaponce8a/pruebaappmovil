package org.example.project.view.seguridad.login


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
    val errorGeneral: String? = null,
    val isLoading: Boolean = false,
    val isButtonEnabled: Boolean = false,
    // estado autenticacion
    val esAutenticacionNormal: Boolean = false,
    val esAutenticacionOtp: Boolean = false,
    val esCambioClave: Boolean = false,
    val autenticacionCorrecta: Boolean = false,
)
