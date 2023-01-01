package rs.rocketbyte.wifisilencer.core.commons.ext

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.isDndPermissionGranted(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return true
    return ContextCompat.getSystemService(this, NotificationManager::class.java)
        ?.isNotificationPolicyAccessGranted
        ?: false
}