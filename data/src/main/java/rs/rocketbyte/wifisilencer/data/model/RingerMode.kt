package rs.rocketbyte.wifisilencer.data.model

import android.media.AudioManager.*
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(value = RingerMode.Serializer::class)
enum class RingerMode(val ringerMode: Int, private val localReference: Int) {

    NORMAL(RINGER_MODE_NORMAL, 0), SILENT(RINGER_MODE_SILENT, 1), VIBRATE(RINGER_MODE_VIBRATE, 2);

    companion object {
        fun fromRingerMode(mode: Int?): RingerMode? = values().find { it.ringerMode == mode }
        private fun fromLocalReference(mode: Int?): RingerMode? =
            values().find { it.localReference == mode }
    }

    class Serializer : JsonSerializer<RingerMode?>, JsonDeserializer<RingerMode?> {
        override fun serialize(
            src: RingerMode?,
            typeOfSrc: Type?,
            context: JsonSerializationContext
        ): JsonElement {
            return context.serialize(src?.localReference)
        }

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): RingerMode {
            return try {
                fromLocalReference(json.asNumber.toInt()) ?: NORMAL
            } catch (e: JsonParseException) {
                NORMAL
            }
        }
    }

}