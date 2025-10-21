package comm.cliente.en.joyeria.core.Util

import android.util.Base64

actual fun String.toBase64(): String =
    Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

actual fun ByteArray.toBase64(): String =
    Base64.encodeToString(this, Base64.NO_WRAP)

actual fun String.fromBase64(): String =
    String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)
