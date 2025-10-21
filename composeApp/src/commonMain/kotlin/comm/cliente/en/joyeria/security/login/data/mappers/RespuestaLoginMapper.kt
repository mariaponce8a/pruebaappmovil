package comm.cliente.en.joyeria.security.login.data.mappers

import comm.cliente.en.joyeria.security.login.data.dto.RespuestaLoginDto
import comm.cliente.en.joyeria.security.login.domain.RespuestaLogin


fun RespuestaLoginDto.toDomain(): RespuestaLogin {
    return RespuestaLogin(
        idrespuesta = idrespuesta,
        mensaje = mensaje.toDomain()
    )
}