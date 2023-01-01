package rs.rocketbyte.wifisilencer.data.util

import android.content.Context
import rs.rocketbyte.wifisilencer.data.local.sharedprefs.PrefsLocalDataSource
import rs.rocketbyte.wifisilencer.data.repository.DefaultRepository
import rs.rocketbyte.wifisilencer.data.repository.Repository

object RepositoryInjector {

    fun getRepository(context: Context): Repository =
        DefaultRepository(PrefsLocalDataSource(context))

}