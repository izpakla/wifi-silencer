package rs.rocketbyte.wifisilencer.core.model

import android.media.AudioManager

enum class RingerMode(val amRingerMode: Int) {
    NORMAL(AudioManager.RINGER_MODE_NORMAL),
    SILENT(AudioManager.RINGER_MODE_SILENT),
    VIBRATE(AudioManager.RINGER_MODE_VIBRATE);

    companion object {
        fun from(amRingerMode: Int): RingerMode {
            return when (amRingerMode) {
                AudioManager.RINGER_MODE_SILENT -> SILENT
                AudioManager.RINGER_MODE_VIBRATE -> VIBRATE
                else -> NORMAL
            }
        }
    }
}