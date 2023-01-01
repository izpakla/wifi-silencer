package rs.rocketbyte.wifisilencer.data.local.sharedprefs.storage

import java.lang.reflect.Type

internal interface DataProvider {

    fun <T> readValue(key: String, type: Type): T?

    fun <T> writeValue(key: String, value: T)

    fun remove(key: String)

    fun clear()
}