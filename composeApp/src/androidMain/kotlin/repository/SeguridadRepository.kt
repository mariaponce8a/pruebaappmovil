package repository

import model.login.LoginPayload
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import core.network.safeCall

class SeguridadRepository(private val httpClient: HttpClient) {

    private val BASE_URL = "http://190.10.150.187:7230/api"

    suspend fun login(payload: LoginPayload): Result<JsonElement> {
        return safeCall(
            execute = {
                httpClient.post("$BASE_URL/seguridad/login") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }
            },
            transform = { response ->
                Json.parseToJsonElement(response.bodyAsText())
            }
        )
    }
}