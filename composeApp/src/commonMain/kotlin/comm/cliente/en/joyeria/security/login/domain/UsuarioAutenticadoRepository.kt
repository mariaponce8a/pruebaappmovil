package comm.cliente.en.joyeria.security.login.domain

import comm.cliente.en.joyeria.core.domain.DataError
import comm.cliente.en.joyeria.core.domain.Result

interface UsuarioAutenticadoRepository {
    suspend fun login(body: LoginPayload): Result<RespuestaLogin, DataError.Remote>

}