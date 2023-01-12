package rs.rocketbyte.wifisilencer.core.notification

import android.app.Notification

interface NotificationCreator {
    fun showDndPermissionMissing()
    fun createServiceNotification(): Notification
}