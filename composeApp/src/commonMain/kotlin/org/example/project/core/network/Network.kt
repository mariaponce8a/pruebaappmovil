package org.example.project.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

suspend inline fun <T> safeCall(
    execute: () -> HttpResponse,
    transform: (HttpResponse) -> T
): Result<T> {
    return try {
        val response = execute()
        if (response.status.isSuccess()) {
            Result.success(transform(response))
        } else {
            Result.failure(Exception("Error HTTP ${response.status.value}: ${response.status.description}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}



expect fun provideHttpClient(): HttpClient
