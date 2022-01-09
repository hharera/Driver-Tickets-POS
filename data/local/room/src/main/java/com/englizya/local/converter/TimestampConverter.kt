package com.englizya.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.firebase.Timestamp

@ProvidedTypeConverter
class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(value: Timestamp?): Long? {
        return value?.seconds
    }

    @TypeConverter
    fun dateToTimestamp(date: Long?): Timestamp? {
        return date?.let {
            Timestamp(it, 0)
        }
    }
}