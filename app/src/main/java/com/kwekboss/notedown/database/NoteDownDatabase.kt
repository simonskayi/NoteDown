package com.kwekboss.notedown.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.model.NoteDao
import com.kwekboss.notedown.utils.Converter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NoteDownDatabase(): RoomDatabase() {

    abstract fun getNoteDAO():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDownDatabase?=null
        fun getDatabase(context: Context):NoteDownDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDownDatabase::class.java,
                    "note_dataBase"
                ).build()
                INSTANCE=instance
                return instance
            }
           }
    }
}