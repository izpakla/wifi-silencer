package rs.rocketbyte.wifisilencer.core.model.mapper

import rs.rocketbyte.wifisilencer.core.model.RingerMode

fun RingerMode.map(): rs.rocketbyte.wifisilencer.data.model.RingerMode {
    return when (this) {
        RingerMode.NORMAL -> rs.rocketbyte.wifisilencer.data.model.RingerMode.NORMAL
        RingerMode.SILENT -> rs.rocketbyte.wifisilencer.data.model.RingerMode.SILENT
        RingerMode.VIBRATE -> rs.rocketbyte.wifisilencer.data.model.RingerMode.VIBRATE
    }
}

fun rs.rocketbyte.wifisilencer.data.model.RingerMode.map(): RingerMode {
    return when (this) {
        rs.rocketbyte.wifisilencer.data.model.RingerMode.NORMAL -> RingerMode.NORMAL
        rs.rocketbyte.wifisilencer.data.model.RingerMode.SILENT -> RingerMode.SILENT
        rs.rocketbyte.wifisilencer.data.model.RingerMode.VIBRATE -> RingerMode.VIBRATE
    }
}