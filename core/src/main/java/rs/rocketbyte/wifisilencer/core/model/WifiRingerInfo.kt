package rs.rocketbyte.wifisilencer.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class WifiRingerInfo constructor(
    val ssid: String,
    val description: String?,
    val dateAdded: Date,
    val ringerMode: RingerMode
) : Parcelable
