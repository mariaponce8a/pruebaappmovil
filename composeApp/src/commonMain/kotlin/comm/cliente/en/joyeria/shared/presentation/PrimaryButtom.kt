package comm.cliente.en.joyeria.shared.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import comm.cliente.en.joyeria.CustomTheme

@Composable
fun PrimaryButtom(
    modifier: Modifier = Modifier,
    isPrimary: Boolean,
    text: String,
    onClick: () -> Unit,
    showIcon: Boolean = false,
    Icon: ImageVector?,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
) {
    CustomTheme {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPrimary) Color(0xFFFDC300) else Color(0xFF0C3260),
                contentColor = if (isPrimary) Color(0xFF0C3260) else Color.White,
                disabledContainerColor = if (isPrimary) Color(0xFFEED692) else Color(0xFF6C8EBA),
                disabledContentColor = if (isPrimary) Color(0xFF386497) else Color.White,
            ),
            onClick = {
                println("🔥 BOTÓN PRESIONADO 🔥")
                onClick
            }
        ) {
            AnimatedVisibility(
                visible = showIcon,
            ) {
                Icon(
                    imageVector = Icon ?: Icons.Default.Search,
                    contentDescription = null,
                )
            }
            Text(
                text = text.uppercase(),
                style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = if (isPrimary) Color(0xFF386497) else Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(20.dp)
                )
            }
        }
    }
}