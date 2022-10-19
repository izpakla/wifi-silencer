package rs.rocketbyte.wifisilencer.data.util

import android.content.Context
import rs.rocketbyte.wifisilencer.data.local.DefaultLocalDataSource
import rs.rocketbyte.wifisilencer.data.local.db.AppDatabase
import rs.rocketbyte.wifisilencer.data.remote.DefaultRemoteDataSource
import rs.rocketbyte.wifisilencer.data.repository.DefaultRepository
import rs.rocketbyte.wifisilencer.data.repository.Repository

object RepositoryInjector {

    fun getRepository(
        context: Context
    ): Repository = DefaultRepository(
        DefaultRemoteDataSource(),
        DefaultLocalDataSource(AppDatabase.getInstance(context))
    )

}