package org.example.project.shared.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun FormsSimpleInput(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector?,
    showTrailingIcon: Boolean = false,
    showLeadingIcon: Boolean = false,
    trailingIconAction: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    hideText: Boolean = false,
    onUsuarioTouched: (String) -> Unit,
) {
    var isUsuarioFocused by remember { mutableStateOf(false) }
    MaterialTheme {
        CompositionLocalProvider(
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = Color(163, 129, 16),
                backgroundColor = Color(163, 129, 16)
            )
        ) {
            Column {
                //External label
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                //campo
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color(0xFF79747E),
                        focusedIndicatorColor = Color(163, 129, 16),
                    ),
                    visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
                    leadingIcon = {
                        AnimatedVisibility(
                            visible = showLeadingIcon
                        ) {
                            Icon(
                                imageVector = leadingIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                            )
                        }
                    },
                    trailingIcon =
                        {
                            AnimatedVisibility(
                                visible = showTrailingIcon
                            ) {
                                IconButton(
                                    onClick = { trailingIconAction("") },
                                ) {
                                    Icon(
                                        imageVector = trailingIcon ?: Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                                    )
                                }
                            }
                        },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),

                    isError = hasError,
                    modifier = modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isUsuarioFocused = true
                            }
                            if (!focusState.isFocused && isUsuarioFocused) {
                                onUsuarioTouched(value)
                            }
                        }
                )
                if (hasError) {
                    Text(
                        text = errorMessage.toString(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }

            }
        }
    }
}