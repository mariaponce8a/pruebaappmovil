package comm.cliente.en.joyeria.security.login.data.repository

import comm.cliente.en.joyeria.core.Util.toBase64
import comm.cliente.en.joyeria.core.domain.DataError
import comm.cliente.en.joyeria.core.domain.Result
import comm.cliente.en.joyeria.core.domain.map
import comm.cliente.en.joyeria.security.login.data.mappers.toDomain
import comm.cliente.en.joyeria.security.login.data.network.RemoteUsuarioAutenticadoDataSource
import comm.cliente.en.joyeria.security.login.domain.LoginPayload
import comm.cliente.en.joyeria.security.login.domain.RespuestaLogin
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticadoRepository

class UsuarioAutenticadoRepositoryImpl(
    private val remoteUserDataSource: RemoteUsuarioAutenticadoDataSource
): UsuarioAutenticadoRepository {
    override suspend fun login(body: LoginPayload): Result<RespuestaLogin, DataError.Remote> {

        val bodyModificado = body.copy(clave = body.clave.toBase64())
        return remoteUserDataSource.login(bodyModificado).map {
            dto -> dto.toDomain()
        }
    }
}