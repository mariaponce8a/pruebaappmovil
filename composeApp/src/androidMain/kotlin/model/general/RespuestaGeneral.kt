package model.general

import kotlinx.serialization.json.JsonElement

data class RespuestaGeneral (
    val idrespuesta: Int,
    val mensaje: JsonElement//UsuarioAutenticado
)