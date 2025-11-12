package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import app_firma_digital.org.example.project.core.di.initKoin
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.project.view.seguridad.login.LoginScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(
            screen =
                LoginScreen()
        ) { navigator ->
            SlideTransition(navigator)
        }
    }
}
