package rs.rocketbyte.wifisilencer.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class WifiData internal constructor(
    @SerializedName("ssid")
    val ssid: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dateAdded")
    val dateAdded: Date,
    @SerializedName("type")
    val ringerMode: RingerMode
)
