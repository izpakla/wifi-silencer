package rs.rocketbyte.wifisilencer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.text.TextUtils
import android.util.Log
import rs.rocketbyte.wifisilencer.core.commons.ext.bundleToString
import rs.rocketbyte.wifisilencer.core.receivers.WifiReceiver

class WifiReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d(TAG, "action: $action")
        Log.d(TAG, "bundle: " + intent.extras.bundleToString())

        if (TextUtils.equals(WifiManager.WIFI_STATE_CHANGED_ACTION, action)) {
            when (intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_DISABLING -> {}
                WifiManager.WIFI_STATE_DISABLED -> onConnectionStateChanged(context, false)
                WifiManager.WIFI_STATE_ENABLING -> {}
                WifiManager.WIFI_STATE_ENABLED -> onConnectionStateChanged(context, true)
                WifiManager.WIFI_STATE_UNKNOWN -> {}
                else -> {}
            }
        } else if (TextUtils.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION, action)) {
            when (intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)?.state) {
                NetworkInfo.State.CONNECTED -> onConnectionStateChanged(context, true)
                NetworkInfo.State.CONNECTING -> {}
                NetworkInfo.State.DISCONNECTED -> onConnectionStateChanged(context, false)
                NetworkInfo.State.DISCONNECTING -> {}
                NetworkInfo.State.SUSPENDED -> {}
                NetworkInfo.State.UNKNOWN -> {}
                else -> {}
            }
        } else if (TextUtils.equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION, action)) {
            when (intent.getParcelableExtra<SupplicantState>(WifiManager.EXTRA_NEW_STATE)) {
                SupplicantState.ASSOCIATED -> {}
                SupplicantState.ASSOCIATING -> {}
                SupplicantState.AUTHENTICATING -> {}
                SupplicantState.COMPLETED -> onConnectionStateChanged(context, true)
                SupplicantState.DISCONNECTED -> onConnectionStateChanged(context, false)
                SupplicantState.DORMANT -> {}
                SupplicantState.FOUR_WAY_HANDSHAKE -> {}
                SupplicantState.GROUP_HANDSHAKE -> {}
                SupplicantState.INACTIVE -> {}
                SupplicantState.INTERFACE_DISABLED -> {}
                SupplicantState.INVALID -> {}
                SupplicantState.SCANNING -> {}
                SupplicantState.UNINITIALIZED -> {}
                else -> {}
            }
        }
    }

    private fun onConnectionStateChanged(context: Context, isConnected: Boolean) {
        // TODO Notify Service
    }

    companion object {
        private val TAG = WifiReceiver::class.java.simpleName
    }
}