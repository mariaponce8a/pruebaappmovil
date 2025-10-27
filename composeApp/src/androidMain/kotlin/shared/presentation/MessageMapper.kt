package shared.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app_firma_electronica.composeapp.generated.resources.Res
import app_firma_electronica.composeapp.generated.resources.apierr
import app_firma_electronica.composeapp.generated.resources.dnp
import app_firma_electronica.composeapp.generated.resources.fcr
import app_firma_electronica.composeapp.generated.resources.ualif
import app_firma_electronica.composeapp.generated.resources.ub
import app_firma_electronica.composeapp.generated.resources.uci
import app_firma_electronica.composeapp.generated.resources.une
import org.jetbrains.compose.resources.stringResource

@Composable
fun getStringByCode(code: String?): String {
    if (code.isNullOrBlank()) return ""
    return when (code.lowercase()) {
        "uci" -> stringResource(Res.string.uci)
        "fcr" -> stringResource(Res.string.fcr)
        "ualif" -> stringResource(Res.string.ualif)
        "ub" -> stringResource(Res.string.ub)
        "une" -> stringResource(Res.string.une)
        "apierr" -> stringResource(Res.string.apierr)
        "dnp" -> stringResource(Res.string.dnp)
        else -> stringResource(Res.string.apierr)
    }
}