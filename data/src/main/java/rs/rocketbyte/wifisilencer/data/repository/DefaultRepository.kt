package rs.rocketbyte.wifisilencer.data.repository

import rs.rocketbyte.wifisilencer.data.local.LocalDataSource
import rs.rocketbyte.wifisilencer.data.remote.RemoteDataSource


internal class DefaultRepository(
    private val apiDataSource: RemoteDataSource,
    private val dbDataSource: LocalDataSource
) : Repository {

}