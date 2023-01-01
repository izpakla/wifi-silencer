package rs.rocketbyte.wifisilencer.data.repository

import rs.rocketbyte.wifisilencer.data.local.LocalDataSource
import rs.rocketbyte.wifisilencer.data.model.RingerMode
import rs.rocketbyte.wifisilencer.data.model.WifiData
import java.util.*


internal class DefaultRepository(private val localDataSource: LocalDataSource) : Repository {

    override fun getDefaultRingerMode(currentRingerMode: RingerMode): RingerMode =
        synchronized(localDataSource) {
            val ringerMode = localDataSource.getDefaultRingerMode()
            return if (ringerMode == null) {
                localDataSource.setDefaultRingerMode(currentRingerMode)
                currentRingerMode
            } else {
                ringerMode
            }
        }

    override fun setDefaultRingerMode(ringerMode: RingerMode) = synchronized(localDataSource) {
        localDataSource.setDefaultRingerMode(ringerMode)
    }

    override fun getRingerMode(ssid: String): RingerMode? = synchronized(localDataSource) {
        localDataSource.getWifiDataList()?.find { it.ssid == ssid }?.ringerMode
    }

    override fun setRingerMode(ssid: String, ringerMode: RingerMode): Boolean =
        synchronized(localDataSource) {
            val data = localDataSource.getWifiDataList()
            val i = data?.indexOfFirst { it.ssid == ssid }?.takeIf { it != -1 }
                ?: return@synchronized false

            data[i] = data[i].copy(ringerMode = ringerMode)
            localDataSource.setWifiDataList(data)

            return@synchronized true
        }

    override fun addWifiData(ssid: String, description: String?, ringerMode: RingerMode): Boolean =
        synchronized(localDataSource) {
            val data = localDataSource.getWifiDataList() ?: ArrayList()
            if (data.find { it.ssid == ssid } != null) return@synchronized false
            data.add(WifiData(ssid, description, Date(), ringerMode))
            localDataSource.setWifiDataList(data)
            return@synchronized true
        }

    override fun updateDescription(ssid: String, description: String?): Boolean =
        synchronized(localDataSource) {
            val data = localDataSource.getWifiDataList()
            val i = data?.indexOfFirst { it.ssid == ssid }?.takeIf { it != -1 }
                ?: return@synchronized false

            data[i] = data[i].copy(description = description)
            localDataSource.setWifiDataList(data)

            return@synchronized true
        }

    override fun isMonitoringEnabled(): Boolean = synchronized(localDataSource) {
        localDataSource.isMonitorServiceStarted()
    }

    override fun setMonitoringEnabled(enabled: Boolean) = synchronized(localDataSource) {
        localDataSource.setMonitorServiceStarted(enabled)
    }
}