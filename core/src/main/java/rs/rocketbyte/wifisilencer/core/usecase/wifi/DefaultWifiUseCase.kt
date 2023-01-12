package rs.rocketbyte.wifisilencer.core.usecase.wifi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat

internal class DefaultWifiUseCase(private val context: Context) : WifiUseCase {

    override fun getCurrentSsid(): String? = context.getCurrentSsid()

    override fun isConnectedToWifi(): Boolean = context.isConnectedToWiFi()

}

private fun Context.getCurrentSsid(): String? {
    return try {
        ContextCompat.getSystemService(
            this,
            WifiManager::class.java
        )?.connectionInfo?.ssid?.cleanSsid().takeIf { !it.equals("<unknown ssid>", true) }
    } catch (t: Throwable) {
        null
    }
}

private fun String.cleanSsid(): String {
    return if (length > 1 && startsWith("\"") && endsWith("\"")) {
        substring(1, length - 1)
    } else {
        this
    }
}

private fun Context.isConnectedToWiFi(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.type == ConnectivityManager.TYPE_WIFI
}