package model.login
import kotlinx.serialization.Serializable

@Serializable
data class LoginPayload (
    val usuario: String,
    val clave: String,
    val mac: String?,
    val so: String?,
    val ip: String?
)