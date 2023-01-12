package rs.rocketbyte.wifisilencer.data.local

import rs.rocketbyte.wifisilencer.data.model.RingerMode
import rs.rocketbyte.wifisilencer.data.model.WifiData

interface LocalDataSource {
    fun getDefaultRingerMode(): RingerMode?
    fun setDefaultRingerMode(ringerMode: RingerMode)
    fun setWifiDataList(wifiDataList: List<WifiData>)
    fun getWifiDataList(): List<WifiData>
    fun isMonitorServiceStarted(): Boolean
    fun setMonitorServiceStarted(started: Boolean)
}