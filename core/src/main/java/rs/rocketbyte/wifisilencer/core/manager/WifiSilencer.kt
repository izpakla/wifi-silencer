package rs.rocketbyte.wifisilencer.core.manager

import kotlinx.coroutines.flow.Flow
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo

interface WifiSilencer {
    val defaultRingerMode: Flow<RingerMode?>
    val wifiRingerInfoList: Flow<List<WifiRingerInfo>>

    var monitoringEnabled: Boolean

    fun setDefaultRingerMode(ringerMode: RingerMode)

    fun addWifiData(wifiRingerInfo: WifiRingerInfo)
    fun updateWifiRingerInfo(wifiRingerInfo: WifiRingerInfo)
    fun removeWifiData(wifiRingerInfo: WifiRingerInfo)
}