package comm.cliente.en.joyeria.security.login.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RespuestaLoginDto(
    @SerialName("idrespuesta") val idrespuesta: Int,
    @SerialName("mensaje") val mensaje: UsuarioAutenticadoDto,
)