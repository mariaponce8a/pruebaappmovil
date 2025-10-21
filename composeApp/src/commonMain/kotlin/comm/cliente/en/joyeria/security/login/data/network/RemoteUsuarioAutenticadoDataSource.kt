package comm.cliente.en.joyeria.security.login.data.network

import comm.cliente.en.joyeria.security.login.data.dto.RespuestaLoginDto
import comm.cliente.en.joyeria.core.domain.DataError
import comm.cliente.en.joyeria.core.domain.Result
import comm.cliente.en.joyeria.security.login.data.dto.UsuarioAutenticadoDto
import comm.cliente.en.joyeria.security.login.domain.LoginPayload
import comm.cliente.en.joyeria.security.login.domain.RespuestaLogin
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticado

interface RemoteUsuarioAutenticadoDataSource {
    suspend fun login(body: LoginPayload): Result<RespuestaLoginDto, DataError.Remote>

}