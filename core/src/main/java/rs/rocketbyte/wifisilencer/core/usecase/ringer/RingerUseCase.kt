package rs.rocketbyte.wifisilencer.core.usecase.ringer

import rs.rocketbyte.wifisilencer.core.model.RingerMode

interface RingerUseCase {
    fun getCurrentRingerMode(): RingerMode
    fun setRingerMode(ringerMode: RingerMode)
}