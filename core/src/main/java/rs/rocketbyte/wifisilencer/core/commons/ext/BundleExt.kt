package rs.rocketbyte.wifisilencer.core.commons.ext

import android.os.Bundle

fun Bundle?.bundleToString(): String? {
    if (this == null) {
        return null
    }
    val string = StringBuilder("Bundle{")
    for (key in keySet()) {
        string.append(" ").append(key).append(" => ").append(this.getString(key)).append(";")
    }
    string.append(" }Bundle")
    return string.toString()
}