package comm.cliente.en.joyeria

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import comm.cliente.en.joyeria.shared.presentation.FormsSimpleInput

@Preview
@Composable
private fun preview() {
    MaterialTheme {
        //funcion para probar componentes compratidos individualmente
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
            FormsSimpleInput(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.Person,
                placeholder = "ingresa la prueba",
                label = "Prueba",
                trailingIcon = Icons.Default.Person,
                showTrailingIcon = true,
                trailingIconAction = {}
            )
        }
    }
}