package org.example.project.core.utils

import io.ktor.utils.io.core.String
import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64

expect fun String.toBase64(): String
expect fun ByteArray.toBase64(): String
expect fun String.fromBase64(): String
expect fun obtenerNombreDispositivo(): String
expect fun obtenerIpDispositivo(): String
expect fun obtenerSoDispositivo(): String