package org.example.project.view.seguridad.otp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class OtpScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(){
            MaterialTheme {
                Text(text = "Otp Screen")
            }
        }
    }

}