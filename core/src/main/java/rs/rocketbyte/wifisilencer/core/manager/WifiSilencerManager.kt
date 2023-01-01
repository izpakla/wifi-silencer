package rs.rocketbyte.wifisilencer.core.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.mapper.map
import rs.rocketbyte.wifisilencer.core.notification.NotificationCreator
import rs.rocketbyte.wifisilencer.core.usecase.dnd.DndUseCase
import rs.rocketbyte.wifisilencer.core.usecase.ringer.RingerUseCase
import rs.rocketbyte.wifisilencer.core.usecase.wifi.WifiUseCase
import rs.rocketbyte.wifisilencer.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WifiSilencerManager @Inject constructor(
    private val repository: Repository,
    private val wifiUseCase: WifiUseCase,
    private val ringerUseCase: RingerUseCase,
    private val dndUseCase: DndUseCase,
    private val notificationCreator: NotificationCreator
) : WifiSilencer, WifiConnectionListener {

    private val managerJob = SupervisorJob()
    private val managerScope = CoroutineScope(IO + managerJob)

    override fun onConnectionChanged(isConnected: Boolean) {
        managerScope.launch {
            readWifi(isConnected)
        }
    }

    override fun setDefaultRingerMode(ringerMode: RingerMode) {
        repository.setDefaultRingerMode(ringerMode.map())
        recheck()
    }

    override fun setRingerMode(ssid: String, ringerMode: RingerMode) {
        if (repository.setRingerMode(ssid, ringerMode.map())) {
            recheck()
        }
    }

    override fun addWifiData(
        ssid: String,
        description: String?,
        ringerMode: RingerMode
    ) {
        if (repository.addWifiData(ssid, description, ringerMode.map())) {
            recheck()
        }
    }

    override fun updateDescription(ssid: String, description: String?) {
        if (repository.updateDescription(ssid, description)) {
            recheck()
        }
    }

    override fun isMonitoringEnabled(): Boolean = repository.isMonitoringEnabled()

    private fun recheck() {
        managerScope.launch {
            readWifi(wifiUseCase.isConnectedToWifi())
        }
    }

    private suspend fun readWifi(isConnected: Boolean) {
        if (isConnected) {
            // Try 3 times if cannot read SSID, do nothing
            repeat(3) {
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

    private fun onWifiNotConnected() {
        setDefaultRingerMode()
    }

    private fun onWifiConnected(ssid: String) {
        val ringerMode = repository.getRingerMode(ssid)
        if (ringerMode != null) {
            setRingerMode(ringerMode.map())
        } else {
            setDefaultRingerMode()
        }
    }

    private fun setDefaultRingerMode() {
        val default = repository.getDefaultRingerMode(ringerUseCase.getCurrentRingerMode().map())
        setRingerMode(default.map())
    }

    private fun setRingerMode(newRingerMode: RingerMode) {
        if (newRingerMode == RingerMode.SILENT && dndUseCase.isDndPermissionGranted() && ringerUseCase.getCurrentRingerMode() != newRingerMode) {
            notificationCreator.buildDndNotification()
        } else {
            ringerUseCase.setRingerMode(newRingerMode)
        }
    }

}