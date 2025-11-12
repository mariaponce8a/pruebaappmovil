package org.example.project.model.login
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPayload (
    @SerialName("Usuario") val usuario: String,
    @SerialName("Clave")val clave: String,
    @SerialName("Mac")val mac: String?,
    @SerialName("So")val so: String?,
    @SerialName("DireccionIp")val ip: String?
)