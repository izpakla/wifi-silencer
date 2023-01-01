package rs.rocketbyte.wifisilencer.core.usecase.ringer

import android.content.Context
import android.media.AudioManager
import androidx.core.content.ContextCompat
import rs.rocketbyte.wifisilencer.core.model.RingerMode

class DefaultRingerUseCase(private val context: Context) : RingerUseCase {

    override fun getCurrentRingerMode(): RingerMode = context.getRingerMode() ?: RingerMode.NORMAL

    override fun setRingerMode(ringerMode: RingerMode) {
        context.setRingerMode(ringerMode)
    }

}

private fun Context.getRingerMode(): RingerMode? {
    return ContextCompat.getSystemService(this, AudioManager::class.java)?.let {
        RingerMode.from(it.ringerMode)
    }
}

private fun Context.setRingerMode(ringerMode: RingerMode) {
    ContextCompat.getSystemService(this, AudioManager::class.java)?.let {
        it.ringerMode = ringerMode.amRingerMode
    }
}