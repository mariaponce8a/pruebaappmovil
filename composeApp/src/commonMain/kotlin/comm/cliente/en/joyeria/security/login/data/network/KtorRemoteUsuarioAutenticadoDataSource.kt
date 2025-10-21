package comm.cliente.en.joyeria.security.login.data.network

import comm.cliente.en.joyeria.core.data.safeCall
import comm.cliente.en.joyeria.core.domain.DataError
import comm.cliente.en.joyeria.core.domain.Result
import comm.cliente.en.joyeria.security.login.data.dto.RespuestaLoginDto
import comm.cliente.en.joyeria.security.login.data.dto.UsuarioAutenticadoDto
import comm.cliente.en.joyeria.security.login.domain.LoginPayload
import comm.cliente.en.joyeria.security.login.domain.RespuestaLogin
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticado
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val BASE_URL = "http://190.10.150.187:7230/api"

class KtorRemoteUserDataSource(
    private val httpClient: HttpClient
): RemoteUsuarioAutenticadoDataSource {

    override suspend fun login(
        body: LoginPayload
    ): Result<RespuestaLoginDto, DataError.Remote> {
        return safeCall {
            httpClient.post(
                urlString = "$BASE_URL/seguridad/login"
            ){
                println("Usuario =>"+body.usuario.toString()+"   Clave =>"+ body.clave.toString())
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

}