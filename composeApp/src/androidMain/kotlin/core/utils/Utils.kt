package core.utils

import android.content.Context
import android.provider.Settings
import android.util.Base64
import java.net.NetworkInterface
import java.util.Collections

lateinit var appContext: Context
fun String.toBase64(): String =
    Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

fun ByteArray.toBase64(): String =
    Base64.encodeToString(this, Base64.NO_WRAP)

fun String.fromBase64(): String =
    String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)

fun ObtenerNombreDispositivo(): String {
    return Settings.Global.getString(appContext.contentResolver, Settings.Global.DEVICE_NAME)
        ?: Settings.Secure.getString(appContext.contentResolver, "bluetooth_name")
}

fun ObtenerSoDispositivo(): String {
    return "Android"
}

fun ObtenerIpDispositivo(): String {
    try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (intf in interfaces) {
            val addrs = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                if (!addr.isLoopbackAddress && addr.hostAddress.indexOf(':') < 0) {
                    return addr.hostAddress ?: "Unknown IP"
                }
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return "IP Desconocida"
}