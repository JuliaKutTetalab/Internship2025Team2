package com.example.growbox.data.model
import androidx.room.TypeConverter
import com.google.firebase.Timestamp

class Converters {

    @TypeConverter
    fun fromTimestamp(ts: Timestamp?): Long? = ts?.seconds

    @TypeConverter
    fun toTimestamp(seconds: Long?): Timestamp? =
        seconds?.let { Timestamp(it, 0) }
}