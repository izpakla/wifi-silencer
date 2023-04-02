package rs.rocketbyte.wifisilencer.ui.commons

import android.os.Build
import android.os.Bundle

@Suppress("DEPRECATION")
fun <T> Bundle.getParcelableOrNull(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        getParcelable(key)
    }
}