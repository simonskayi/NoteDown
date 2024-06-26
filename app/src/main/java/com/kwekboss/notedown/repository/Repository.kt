package com.kwekboss.notedown.repository

import androidx.lifecycle.LiveData
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.model.NoteDao
import kotlinx.coroutines.flow.Flow

class Repository(private val notes:NoteDao) {

    suspend fun newNote(note:Note){
        notes.insert(note)
    }

    suspend fun deleteNote(note: Note){
        notes.delete(note)
    }

    suspend fun updateNote(note: Note){
        notes.update(note)
    }
    fun getAllNotes():LiveData<List<Note>> = notes.allNotes()

    fun searchDatabase(searchQuery:String):Flow<List<Note>> = notes.searchQuery(searchQuery)

}