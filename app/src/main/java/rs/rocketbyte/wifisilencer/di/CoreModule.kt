package rs.rocketbyte.wifisilencer.di

import android.app.Notification
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.rocketbyte.wifisilencer.core.notification.NotificationCreator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideNotificationCreator(
        @ApplicationContext context: Context
    ): NotificationCreator = object : NotificationCreator {
        override fun showDndPermissionMissing() {
        }

        override fun createServiceNotification(): Notification {
            TODO("Not yet implemented")
        }
    }

}