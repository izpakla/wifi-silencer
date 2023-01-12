package rs.rocketbyte.wifisilencer.core.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION
import android.net.wifi.WifiManager.SUPPLICANT_STATE_CHANGED_ACTION
import android.os.Build
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import rs.rocketbyte.wifisilencer.core.notification.NotificationCreator
import rs.rocketbyte.wifisilencer.core.receivers.WifiReceiver
import javax.inject.Inject

@AndroidEntryPoint
class WifiMonitorService : Service() {

    private val wifiReceiver = WifiReceiver()

    @Inject
    lateinit var notificationCreator: NotificationCreator


    private val actionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_STOP -> {
                    this@WifiMonitorService.stopForeground(true)
                    this@WifiMonitorService.stopSelf()
                }
            }

        }
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(NOTIFICATION_ID, notificationCreator.createServiceNotification())

        registerReceiver(
            wifiReceiver,
            IntentFilter(NETWORK_STATE_CHANGED_ACTION).apply {
                addAction(SUPPLICANT_STATE_CHANGED_ACTION)
            })

        registerReceiver(actionReceiver, IntentFilter(ACTION_STOP))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(wifiReceiver)
        unregisterReceiver(actionReceiver)
    }

    override fun onBind(intent: Intent): IBinder? = null

    companion object {
        private const val NOTIFICATION_ID = 1

        private const val ACTION_STOP = "rs.rocketbyte.wifisilencer.core.service.ACTION_STOP"


        fun startService(context: Context) {
            val intent = Intent(context, WifiMonitorService::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stopService(context: Context) {
            context.sendBroadcast(Intent(ACTION_STOP))
        }

        private fun isConnectedToWiFi(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.type == ConnectivityManager.TYPE_WIFI
        }
    }
}
