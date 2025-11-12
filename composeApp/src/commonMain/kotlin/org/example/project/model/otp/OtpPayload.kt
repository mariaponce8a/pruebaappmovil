package org.example.project.model.otp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpPayload(
    @SerialName("TipoValidacion") val tipoValidacion: String,
    @SerialName("DireccionIp") val direccionIp: String,
    @SerialName("Mac") val mac: String,
    @SerialName("Correo") val correo: String,
    @SerialName("Usuario") val usuario: String,
    @SerialName("SistemaOperativo") val so: String,
    @SerialName("Otp") val otp: String
)
