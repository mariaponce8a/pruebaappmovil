package comm.cliente.en.joyeria.core.domain

data class ApiError(
    val codigo: String? = null,
    val descripcion: String? = null
) : Error
