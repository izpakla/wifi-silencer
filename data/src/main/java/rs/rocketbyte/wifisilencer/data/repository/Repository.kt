package rs.rocketbyte.wifisilencer.data.repository

import kotlinx.coroutines.flow.Flow
import rs.rocketbyte.wifisilencer.data.model.RingerMode
import rs.rocketbyte.wifisilencer.data.model.WifiData

interface Repository {
    val defaultRingerMode: Flow<RingerMode?>
    val wifiDataList: Flow<List<WifiData>>
    var monitoringEnabled: Boolean

    fun addWifiData(wifiData: WifiData)
    fun updateWifiData(wifiData: WifiData)
    fun removeWifiData(wifiData: WifiData)

    fun updateDefaultRingerMode(ringerMode: RingerMode)
}