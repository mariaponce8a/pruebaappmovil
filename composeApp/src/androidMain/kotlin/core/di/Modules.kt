package core.di

import org.koin.dsl.module


import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import repository.SeguridadRepository
import controller.SeguridadController
import io.ktor.client.engine.okhttp.OkHttp

val appModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }

    single { SeguridadRepository(get()) }
    single { SeguridadController(get()) }
}