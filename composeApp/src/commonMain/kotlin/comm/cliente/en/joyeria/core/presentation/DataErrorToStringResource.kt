package comm.cliente.en.joyeria.core.presentation

import app_cliente_en_joyeria.composeapp.generated.resources.Res
import app_cliente_en_joyeria.composeapp.generated.resources.error_dll
import app_cliente_en_joyeria.composeapp.generated.resources.error_gen
import app_cliente_en_joyeria.composeapp.generated.resources.error_timeout
import app_cliente_en_joyeria.composeapp.generated.resources.error_mp
import app_cliente_en_joyeria.composeapp.generated.resources.error_noNet
import app_cliente_en_joyeria.composeapp.generated.resources.error_parse
import app_cliente_en_joyeria.composeapp.generated.resources.fcr
import comm.cliente.en.joyeria.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Local.DISK_FULL -> Res.string.error_dll
        DataError.Local.UNKNOWN -> Res.string.error_gen
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_mp
        DataError.Remote.NOT_INTERNET -> Res.string.error_noNet
        DataError.Remote.SERVER -> Res.string.error_gen
        DataError.Remote.SERIALIZATION -> Res.string.error_parse
        DataError.Remote.UNKNOWN -> Res.string.error_gen
        DataError.Remote.FCR -> Res.string.fcr
    }

    return UiText.StringResourceId(stringRes)
}