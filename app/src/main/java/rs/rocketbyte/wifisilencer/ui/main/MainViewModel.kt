package rs.rocketbyte.wifisilencer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.rocketbyte.wifisilencer.core.manager.WifiSilencer
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val wifiSilencer: WifiSilencer
) : ViewModel() {

    val wifiDataList: LiveData<List<WifiRingerInfo>> = wifiSilencer.wifiRingerInfoList.asLiveData()
    val defaultMode: LiveData<RingerMode?> = wifiSilencer.defaultRingerMode.asLiveData()

    fun getDefaultMode(): RingerMode {
        TODO("Not yet implemented")
    }

    fun removeWifiData(wifiData: WifiRingerInfo) {
        TODO("Not yet implemented")
    }

    fun getCurrentSsid(): String? {
        TODO("Not yet implemented")
    }

    fun isSsidAdded(ssid: String): Boolean {
        TODO("Not yet implemented")
    }

    fun onSoundModeChanged(wifiData: WifiRingerInfo, soundMode: RingerMode) {
        TODO("Not yet implemented")
    }
}
