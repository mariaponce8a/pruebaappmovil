package model.login

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioAutenticado(
    val idUsuario: Int,
    val usuario: String,
    val clave: String,
    val correo: String,
    val idEstablecimiento: Int,
    val idEstado: Int,
    val intentoFallido: Int,
    val idEstadoValor: String,
    val usuarioCreacion: String,
    val usuarioActualizacion: String,
    val fechaCreacion: String,
    val fechaActualizacion: String,
    val dfaHabilitado: Boolean,
    val cambioClaveHabilitado: Boolean,
    val token: String
    )
