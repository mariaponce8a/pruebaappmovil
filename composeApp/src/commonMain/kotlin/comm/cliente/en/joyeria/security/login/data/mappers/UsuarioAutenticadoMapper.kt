package comm.cliente.en.joyeria.security.login.data.mappers

import comm.cliente.en.joyeria.security.login.data.dto.RespuestaLoginDto
import comm.cliente.en.joyeria.security.login.data.dto.UsuarioAutenticadoDto
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticado
import kotlinx.serialization.SerialName

fun UsuarioAutenticadoDto.toDomain(): UsuarioAutenticado {
    return UsuarioAutenticado(
        idUsuario = idUsuario,
        usuario = usuario,
        clave = clave,
        correo = correo,
        idEstablecimiento = idEstablecimiento,
        idEstado = isEstado,
        intentoFallido = intentoFallido,
        idEstadoValor = idEstadoValor,
        usuarioCreacion = usuarioCreacion,
        usuarioActualizacion = usuarioActualizacion,
        fechaCreacion = fechaCreacion,
        fechaActualizacion = fechaActualizacion,
        dfaHabilitado = dfaHabilitado,
        cambioClaveHabilitado = cambioClaveHabilitado,
        token = token
    )
}