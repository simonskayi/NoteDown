package com.kwekboss.notedown.utils

import androidx.room.TypeConverter
import java.util.Date

class Converter {
    @TypeConverter
    fun  fromTimestampToDate(value:Long?): Date?{
        return value?.let {
            Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?):Long?{
        return date?.time
    }
}