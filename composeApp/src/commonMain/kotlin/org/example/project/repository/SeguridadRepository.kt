package org.example.project.repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.example.project.model.login.LoginPayload
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.core.network.safeCall
import org.example.project.core.utils.obtenerIpDispositivo
import org.example.project.core.utils.obtenerNombreDispositivo
import org.example.project.core.utils.obtenerSoDispositivo
import org.example.project.core.utils.toBase64

class SeguridadRepository(private val httpClient: HttpClient) {
    private val BASE_URL = "http://190.10.150.187:7230/api"
    suspend fun login(payload: LoginPayload): Result<JsonElement> {
        return safeCall(
            execute = {
                val bodyModificado = payload.copy(
                    clave = payload.clave.toBase64(),
                    ip = obtenerIpDispositivo(),
                    so = obtenerSoDispositivo(),
                    mac = obtenerNombreDispositivo()
                )
                println("Body => $payload")
                httpClient.post("$BASE_URL/seguridad/login") {
                    contentType(ContentType.Application.Json)
                    setBody(bodyModificado)
                }
            },
            transform = { response ->
                val body = response.bodyAsText()
                println("Respuesta repository => $body")
                Json.parseToJsonElement(body)
            }
        )
    }
}