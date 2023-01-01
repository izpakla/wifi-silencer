package rs.rocketbyte.wifisilencer.data.local.sharedprefs

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rs.rocketbyte.wifisilencer.data.local.LocalDataSource
import rs.rocketbyte.wifisilencer.data.local.sharedprefs.storage.SharedPrefsDataProvider
import rs.rocketbyte.wifisilencer.data.model.RingerMode
import rs.rocketbyte.wifisilencer.data.model.WifiData

class PrefsLocalDataSource(context: Context) : LocalDataSource {

    private val dataProvider = SharedPrefsDataProvider(context, "wifi_data", Gson())

    override fun getDefaultRingerMode(): RingerMode? {
        return dataProvider.readValue<RingerMode>(DEFAULT_MODE, RingerMode::class.java)
    }

    override fun setDefaultRingerMode(ringerMode: RingerMode) {
        dataProvider.writeValue(DEFAULT_MODE, ringerMode)
    }

    override fun setWifiDataList(wifiDataList: List<WifiData>) {
        dataProvider.writeValue(WIFI_DATA_LIST, wifiDataList)
    }

    override fun getWifiDataList(): ArrayList<WifiData>? {
        val listType = object : TypeToken<ArrayList<WifiData>>() {
        }.type

        return dataProvider.readValue<ArrayList<WifiData>>(WIFI_DATA_LIST, listType)
    }

    override fun isMonitorServiceStarted(): Boolean {
        return dataProvider.readValue(MONITOR_SERVICE_STARTED, Boolean::class.java) ?: false
    }

    override fun setMonitorServiceStarted(started: Boolean) {
        dataProvider.writeValue(MONITOR_SERVICE_STARTED, started)
    }

    companion object {
        private const val DEFAULT_MODE = "DEFAULT_MODE"
        private const val WIFI_DATA_LIST = "WIFI_DATA_LIST"
        private const val MONITOR_SERVICE_STARTED = "MONITOR_SERVICE_STARTED"
    }
}