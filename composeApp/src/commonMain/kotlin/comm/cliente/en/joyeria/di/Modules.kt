package comm.cliente.en.joyeria.di

import androidx.lifecycle.viewmodel.compose.viewModel
import comm.cliente.en.joyeria.core.data.HttpClientFactory
import comm.cliente.en.joyeria.security.login.data.network.KtorRemoteUserDataSource
import comm.cliente.en.joyeria.security.login.data.network.RemoteUsuarioAutenticadoDataSource
import comm.cliente.en.joyeria.security.login.data.repository.UsuarioAutenticadoRepositoryImpl
import comm.cliente.en.joyeria.security.login.domain.UsuarioAutenticadoRepository
import comm.cliente.en.joyeria.security.login.presentation.loginForm.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module
val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteUserDataSource).bind<RemoteUsuarioAutenticadoDataSource>()
    singleOf(::UsuarioAutenticadoRepositoryImpl).bind<UsuarioAutenticadoRepository>()

    viewModelOf(::LoginViewModel)
}