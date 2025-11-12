package org.example.project.model.general

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class RespuestaGeneral (
    val idrespuesta: Int,
    val mensaje: JsonElement
)
@Serializable
data class ErrorBase (
    val codigo: String?,
    val descripcion: String?
)