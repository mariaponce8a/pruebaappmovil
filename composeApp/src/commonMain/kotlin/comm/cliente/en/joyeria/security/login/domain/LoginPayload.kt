package comm.cliente.en.joyeria.security.login.domain

data class LoginPayload (
    val usuario: String,
    val clave: String,
    val mac: String,
    val so: String,
    val ip: String
)