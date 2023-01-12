package rs.rocketbyte.wifisilencer.core.model

import java.util.*


data class WifiRingerInfo constructor(
    val ssid: String,
    val description: String?,
    val dateAdded: Date,
    val ringerMode: RingerMode
)
