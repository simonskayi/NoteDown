package com.kwekboss.notedown.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.model.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDownDatabase(): RoomDatabase() {

    abstract fun getNoteDAO():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDownDatabase?=null
        fun getDatabase(context: Context):NoteDownDatabase{
           return INSTANCE?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,NoteDownDatabase::class.java,
                "note_dataBase"
            ).build()
            INSTANCE = instance
            instance
        }

    }
}
}