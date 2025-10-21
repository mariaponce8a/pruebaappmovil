package comm.cliente.en.joyeria.security.login.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioAutenticadoDto(
    @SerialName("IdUsuario") val idUsuario: Int,
    @SerialName("usuario") val usuario: String,
    @SerialName("clave") val clave: String,
    @SerialName("Correo") val correo: String,
    @SerialName("IdEstablecimiento") val idEstablecimiento: Int,
    @SerialName("IdEstado") val isEstado: Int,
    @SerialName("IntentoFallido") val intentoFallido: Int,
    @SerialName("IdEstadoValor") val idEstadoValor: String,
    @SerialName("UsuarioCreacion") val usuarioCreacion: String,
    @SerialName("UsuarioActualizacion") val usuarioActualizacion: String,
    @SerialName("FechaCreacion") val fechaCreacion: String,
    @SerialName("FechaActualizacion") val fechaActualizacion: String,
    @SerialName("DfaHabilitado") val dfaHabilitado: Boolean,
    @SerialName("CambioClaveHabilitado") val cambioClaveHabilitado: Boolean,
    @SerialName("Token") val token: String
    )