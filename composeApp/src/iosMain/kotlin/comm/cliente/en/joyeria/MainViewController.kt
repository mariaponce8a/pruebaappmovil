package comm.cliente.en.joyeria

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import comm.cliente.en.joyeria.di.initKoin
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }