package com.kwekboss.notedown.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_dataBase")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val tittle:String,
    val noteBody:String
    )
