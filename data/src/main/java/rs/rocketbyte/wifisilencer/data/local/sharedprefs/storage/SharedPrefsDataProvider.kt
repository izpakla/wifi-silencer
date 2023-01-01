package rs.rocketbyte.wifisilencer.data.local.sharedprefs.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import com.google.gson.Gson

import java.lang.reflect.Type

internal class SharedPrefsDataProvider(
    context: Context,
    name: String,
    private val gson: Gson
) : DataProvider {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            "${context.packageName}.$name",
            Context.MODE_PRIVATE
        )

    override fun <T> readValue(key: String, type: Type): T? {
        val string: String? = preferences.getString(key, null)

        var value: T? = null
        if (string != null) {
            try {
                value = gson.fromJson<T>(string, type)
            } catch (t: Throwable) {
                Log.d(TAG, "ReadValue: ", t)
            }

        }
        return value
    }

    override fun <T> writeValue(key: String, value: T) {
        preferences.edit().remove(key).putString(key, gson.toJson(value)).apply()
    }

    override fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {
        private val TAG = SharedPrefsDataProvider::class.java.simpleName
    }

}
