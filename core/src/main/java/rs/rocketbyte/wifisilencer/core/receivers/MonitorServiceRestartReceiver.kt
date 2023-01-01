package rs.rocketbyte.wifisilencer.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import rs.rocketbyte.wifisilencer.core.manager.WifiSilencer
import javax.inject.Inject


@AndroidEntryPoint
class MonitorServiceRestartReceiver : BroadcastReceiver() {

    @Inject
    lateinit var wifiSilencer: WifiSilencer

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MonitorServiceRestart", "action: ${intent.action}")

        if (wifiSilencer.isMonitoringEnabled()) {
            when (intent.action) {
                /*Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_MY_PACKAGE_REPLACED -> WifiMonitorService.startService(
                    context
                )*/
            }
        }
    }

}