package app_firma_digital.org.example.project.core.di

import org.koin.dsl.module
import org.example.project.repository.SeguridadRepository
import app_firma_digital.org.example.project.controller.LoginController
import org.example.project.core.network.provideHttpClient

val appModule = module {
    single { provideHttpClient() }
    single { SeguridadRepository(get()) }
    single { LoginController(get()) }
}