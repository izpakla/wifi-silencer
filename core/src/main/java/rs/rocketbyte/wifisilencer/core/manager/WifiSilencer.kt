package rs.rocketbyte.wifisilencer.core.manager

import rs.rocketbyte.wifisilencer.core.model.RingerMode

interface WifiSilencer {
    fun setDefaultRingerMode(ringerMode: RingerMode)
    fun setRingerMode(ssid: String, ringerMode: RingerMode)
    fun addWifiData(ssid: String, description: String?, ringerMode: RingerMode)
    fun updateDescription(ssid: String, description: String?)
    fun isMonitoringEnabled(): Boolean
}