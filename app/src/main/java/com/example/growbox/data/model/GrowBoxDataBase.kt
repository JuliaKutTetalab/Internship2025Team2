package com.example.growbox.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class, CropEntity::class], // Твої таблиці
    version = 1,
    exportSchema = false
)
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