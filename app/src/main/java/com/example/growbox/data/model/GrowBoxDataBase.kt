package com.example.growbox.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserEntity::class,
        CropEntity::class,
        ChartHistoryEntity::class,
        ChartHourlyEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GrowBoxDataBase : RoomDatabase() {

    abstract fun growBoxDao(): GrowBoxDao

    companion object {
        @Volatile
        private var Instance: GrowBoxDataBase? = null

        fun getDatabase(context: Context): GrowBoxDataBase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GrowBoxDataBase::class.java,
                    "growbox_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}