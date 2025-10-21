package comm.cliente.en.joyeria

import androidx.compose.runtime.*
import comm.cliente.en.joyeria.core.data.HttpClientFactory
import comm.cliente.en.joyeria.di.initKoin
import comm.cliente.en.joyeria.security.login.data.network.KtorRemoteUserDataSource
import comm.cliente.en.joyeria.security.login.data.repository.UsuarioAutenticadoRepositoryImpl
import comm.cliente.en.joyeria.security.login.presentation.loginForm.LoginScreenRoot
import comm.cliente.en.joyeria.security.login.presentation.loginForm.LoginViewModel
import io.ktor.client.engine.HttpClientEngine
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    initKoin()
    val viewModel = koinViewModel<LoginViewModel>()
    LoginScreenRoot(
        viewModel = viewModel,
        onLoginFormSubmit = { loginBody ->
            viewModel.executeLogin(loginBody)
        }
    )
}
