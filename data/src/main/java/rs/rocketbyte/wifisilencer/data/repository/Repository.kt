package rs.rocketbyte.wifisilencer.data.repository

import rs.rocketbyte.wifisilencer.data.model.RingerMode

interface Repository {
    fun getDefaultRingerMode(currentRingerMode: RingerMode): RingerMode
    fun getRingerMode(ssid: String): RingerMode?
    fun setDefaultRingerMode(ringerMode: RingerMode)
    fun setRingerMode(ssid: String, ringerMode: RingerMode): Boolean
    fun addWifiData(ssid: String, description: String?, ringerMode: RingerMode): Boolean
    fun updateDescription(ssid: String, description: String?): Boolean
    fun isMonitoringEnabled(): Boolean
    fun setMonitoringEnabled(enabled: Boolean)
}