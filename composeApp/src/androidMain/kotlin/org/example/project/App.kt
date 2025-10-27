package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import controller.SeguridadController
import core.di.initKoin
import org.koin.compose.koinInject
import view.Seguridad.LoginView

@Composable
fun App() {
    initKoin()
    MaterialTheme {
        val controller: SeguridadController = koinInject()
        LoginView(
            controller = controller,
            onAction = { action ->
                controller.onAction(action)
            }
        )
    }
}
