package rs.rocketbyte.wifisilencer.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import rs.rocketbyte.wifisilencer.data.local.LocalDataSource
import rs.rocketbyte.wifisilencer.data.model.RingerMode
import rs.rocketbyte.wifisilencer.data.model.WifiData


internal class DefaultRepository(private val localDataSource: LocalDataSource) : Repository {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private val _defaultRingerMode = MutableStateFlow(localDataSource.getDefaultRingerMode())
    override val defaultRingerMode: Flow<RingerMode?>
        get() = _defaultRingerMode

    private val _wifiRingerInfoList = MutableStateFlow(localDataSource.getWifiDataList())
    override val wifiDataList: Flow<List<WifiData>>
        get() = _wifiRingerInfoList

    override var monitoringEnabled: Boolean
        get() = localDataSource.isMonitorServiceStarted()
        set(value) {
            localDataSource.setMonitorServiceStarted(value)
        }

    private fun reloadWifiInfoList() {
        coroutineScope.launch {
            _wifiRingerInfoList.emit(localDataSource.getWifiDataList())
        }
    }

    override fun addWifiData(wifiData: WifiData) {
        val data = localDataSource.getWifiDataList().toMutableList()
        if (data.find { it.ssid == wifiData.ssid } != null) return

        data.add(wifiData)
        localDataSource.setWifiDataList(data)

        reloadWifiInfoList()
    }

    override fun updateWifiData(wifiData: WifiData) {
        val data = localDataSource.getWifiDataList().toMutableList()
        val i = data.indexOfFirst { it.ssid == wifiData.ssid }.takeIf { it != -1 } ?: return

        data[i] = data[i].copy(description = wifiData.description)
        localDataSource.setWifiDataList(data)

        reloadWifiInfoList()
    }

    override fun removeWifiData(wifiData: WifiData) {
        val data = localDataSource.getWifiDataList().toMutableList()
        val i = data.indexOfFirst { it.ssid == wifiData.ssid }.takeIf { it != -1 } ?: return

        data.removeAt(i)
        localDataSource.setWifiDataList(data)

        reloadWifiInfoList()
    }

    override fun updateDefaultRingerMode(ringerMode: RingerMode) {
        localDataSource.setDefaultRingerMode(ringerMode)
        coroutineScope.launch {
            _defaultRingerMode.emit(ringerMode)
        }
    }
}