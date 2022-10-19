package rs.rocketbyte.wifisilencer.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.rocketbyte.wifisilencer.data.local.db.dao.ItemsDao
import rs.rocketbyte.wifisilencer.data.local.db.entities.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase private constructor() : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                // Depending on the need use in memory or persistent.
                instance ?: Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java
                ).also {
                    it.fallbackToDestructiveMigration()
                }.build().also { instance = it }
            }
        }
    }
}