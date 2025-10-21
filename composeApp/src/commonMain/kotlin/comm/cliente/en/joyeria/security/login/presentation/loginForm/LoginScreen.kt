package comm.cliente.en.joyeria.security.login.presentation.loginForm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app_cliente_en_joyeria.composeapp.generated.resources.Res
import app_cliente_en_joyeria.composeapp.generated.resources.buttomIngresar
import app_cliente_en_joyeria.composeapp.generated.resources.logoAmarillo
import app_cliente_en_joyeria.composeapp.generated.resources.password
import app_cliente_en_joyeria.composeapp.generated.resources.placeholderContrasena
import app_cliente_en_joyeria.composeapp.generated.resources.placeholderUsuario
import app_cliente_en_joyeria.composeapp.generated.resources.razonDeAplicacion
import app_cliente_en_joyeria.composeapp.generated.resources.user
import comm.cliente.en.joyeria.CustomTheme
import comm.cliente.en.joyeria.security.login.domain.LoginPayload
import comm.cliente.en.joyeria.shared.presentation.FormsSimpleInput
import comm.cliente.en.joyeria.shared.presentation.PrimaryButtom
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginFormSubmit: (LoginPayload) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LoginAction.OnLoginFormSubmit -> {
                    onLoginFormSubmit(
                        LoginPayload(
                            usuario = action.usuario,
                            clave = action.clave,
                            mac = action.mac,
                            so = action.so,
                            ip = action.ip
                        )
                    )
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

// pantalla aislada
@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    CustomTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val center = Offset(constraints.maxWidth / 2f, constraints.maxHeight / 2f)
            var showPasswordText by remember { mutableStateOf(true) }
            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colorStops = arrayOf(
                                0.0f to Color(0xFF0A4893), // centro
                                0.1f to Color(0xFF0A4893),
                                1.0f to Color(0xFF0A203B)  // oscuro borde
                            ),
                            center = center,
                            radius = maxWidth.coerceAtLeast(maxHeight).value.toFloat() // radio dinámico
                        )
                    )
                    .padding(all = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 460.dp)
                        .heightIn(max = 892.dp)
                        .verticalScroll(scrollState)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    //logo app
                    Image(
                        painter = painterResource(Res.drawable.logoAmarillo),
                        contentDescription = "Logo",
                        modifier = Modifier.widthIn(max = 310.dp)
                    )
                    // eslogan app
                    Text(
                        text = stringResource(Res.string.razonDeAplicacion),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(color = Color.White, shape = RoundedCornerShape(size = 15.dp))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        //Campo de usuario
                        FormsSimpleInput(
                            label = stringResource(Res.string.user),
                            value = state.usuario,
                            onValueChange = { onAction(LoginAction.onUserValueChange(it)) },
                            placeholder = stringResource(Res.string.placeholderUsuario),
                            leadingIcon = Icons.Default.Person,
                            showLeadingIcon = true,
                            trailingIcon = null,
                            showTrailingIcon = false,
                            trailingIconAction = {},
                            hideText = false,
                            modifier = Modifier
                                .widthIn(max = 460.dp)
                                .fillMaxWidth()
                        )
                        //Campo de clave
                        FormsSimpleInput(
                            value = state.clave,
                            label = stringResource(Res.string.password),
                            onValueChange = { onAction(LoginAction.onPasswordValueChange(it)) },
                            placeholder = stringResource(Res.string.placeholderContrasena),
                            leadingIcon = Icons.Default.LockPerson,
                            showLeadingIcon = true,
                            trailingIcon = if (showPasswordText) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            showTrailingIcon = true,
                            trailingIconAction = { showPasswordText = !showPasswordText },
                            hideText = showPasswordText,
                            modifier = Modifier
                                .widthIn(max = 460.dp)
                                .fillMaxWidth()
                        )
                        //Boton
                        PrimaryButtom(
                            modifier = Modifier
                                .widthIn(max = 460.dp)
                                .fillMaxWidth(),
                            isPrimary = true,
                            isEnabled = true,
                            text = stringResource(Res.string.buttomIngresar),
                            onClick = {
                                onAction(
                                    LoginAction.OnLoginFormSubmit(
                                        state.usuario,
                                        state.clave,
                                        state.mac,
                                        state.so,
                                        state.direccionIp,
                                    )
                                )
                            },
                            Icon = null,
                            showIcon = false,
                            isLoading = false
                        )
                        //Version
                        Text(
                            text = "V1.0.0",
                            color = Color(0xFF3A3A3A),
                            textAlign = TextAlign.Center,
                        )
                    }
                    //copyright
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Copyright © 2025",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}