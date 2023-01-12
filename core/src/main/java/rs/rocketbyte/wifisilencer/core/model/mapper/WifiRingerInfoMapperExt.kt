package rs.rocketbyte.wifisilencer.core.model.mapper

import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.data.model.WifiData

fun WifiData.map(): WifiRingerInfo {
    return WifiRingerInfo(
        ssid = ssid,
        description = description,
        dateAdded = dateAdded,
        ringerMode = ringerMode.map()
    )
}

fun WifiRingerInfo.map(): WifiData {
    return WifiData(
        ssid = ssid,
        description = description,
        dateAdded = dateAdded,
        ringerMode = ringerMode.map()
    )
}