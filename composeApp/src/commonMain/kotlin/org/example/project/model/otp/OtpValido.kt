package org.example.project.model.otp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpValido(
    @SerialName("Usuario") val usuario: String,
    @SerialName("FechaRegistro") val fechaRegistro: String,
    @SerialName("Mac") val mac: String,
    @SerialName("DireccionIp") val direccionIp: String,
    @SerialName("SistemaOperativo") val so: String,
)
