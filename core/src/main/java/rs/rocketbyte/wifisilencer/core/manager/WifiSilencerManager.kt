package rs.rocketbyte.wifisilencer.core.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.core.model.mapper.map
import rs.rocketbyte.wifisilencer.core.notification.NotificationCreator
import rs.rocketbyte.wifisilencer.core.service.WifiMonitorService
import rs.rocketbyte.wifisilencer.core.usecase.dnd.DndUseCase
import rs.rocketbyte.wifisilencer.core.usecase.ringer.RingerUseCase
import rs.rocketbyte.wifisilencer.core.usecase.wifi.WifiUseCase
import rs.rocketbyte.wifisilencer.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WifiSilencerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository,
    private val wifiUseCase: WifiUseCase,
    private val ringerUseCase: RingerUseCase,
    private val dndUseCase: DndUseCase,
    private val notificationCreator: NotificationCreator
) : WifiSilencer, WifiConnectionListener {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(IO + job)

    override val defaultRingerMode: Flow<RingerMode?>
        get() = repository.defaultRingerMode.map { it?.map() }

    override val wifiRingerInfoList: Flow<List<WifiRingerInfo>>
        get() = repository.wifiDataList.map { wifiDataList -> wifiDataList.map { it.map() } }

    override var monitoringEnabled: Boolean
        get() = repository.monitoringEnabled
        set(value) {
            repository.monitoringEnabled = value
            if (value) {
                WifiMonitorService.startService(context)
            } else {
                WifiMonitorService.stopService(context)
            }
        }

    override fun onConnectionChanged(isConnected: Boolean) {
        coroutineScope.launch {
            readWifi(isConnected)
        }
    }

    override fun setDefaultRingerMode(ringerMode: RingerMode) {
        repository.updateDefaultRingerMode(ringerMode.map())

        recheck()
    }

    override fun updateWifiRingerInfo(wifiRingerInfo: WifiRingerInfo) {
        repository.updateWifiData(wifiRingerInfo.map())

        recheck()
    }

    override fun removeWifiData(wifiRingerInfo: WifiRingerInfo) {
        repository.removeWifiData(wifiRingerInfo.map())

        recheck()
    }

    override fun addWifiData(wifiRingerInfo: WifiRingerInfo) {
        repository.addWifiData(wifiRingerInfo.map())
        recheck()
    }

    private fun recheck() {
        coroutineScope.launch {
            readWifi(wifiUseCase.isConnectedToWifi())
        }
    }

    private suspend fun readWifi(isConnected: Boolean) {
        if (isConnected) {
            repeat(3) { // Retry count
                val currentSsid = wifiUseCase.getCurrentSsid()
                if (!currentSsid.isNullOrBlank()) {
                    onWifiConnected(currentSsid)
                    return@repeat
                }
                delay(500)
            }
        } else {
            onWifiNotConnected()
        }
    }

    private suspend fun onWifiNotConnected() {
        setRingerMode(
            repository.defaultRingerMode.last()?.map()
                ?: ringerUseCase.getCurrentRingerMode()
        )
    }

    private fun onWifiConnected(ssid: String) {
        coroutineScope.launch {
            val ringerMode: WifiRingerInfo? = wifiRingerInfoList.last().find { it.ssid == ssid }
            if (ringerMode != null) {
                setRingerMode(ringerMode.ringerMode)
            } else {
                setRingerMode(
                    repository.defaultRingerMode.last()?.map()
                        ?: ringerUseCase.getCurrentRingerMode()
                )
            }
        }
    }

    private fun setRingerMode(newRingerMode: RingerMode) {
        if (newRingerMode == RingerMode.SILENT
            && dndUseCase.isDndPermissionGranted()
            && ringerUseCase.getCurrentRingerMode() != newRingerMode
        ) {
            notificationCreator.showDndPermissionMissing()
        } else {
            ringerUseCase.setRingerMode(newRingerMode)
        }
    }

}