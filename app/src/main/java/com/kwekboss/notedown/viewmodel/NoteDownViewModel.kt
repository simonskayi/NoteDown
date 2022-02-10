package com.kwekboss.notedown.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kwekboss.notedown.database.NoteDownDatabase
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDownViewModel(application: Application):AndroidViewModel(application){
     private val repository: Repository
    var getAllNote:LiveData<List<Note>>

    init {
       val noteDB = NoteDownDatabase.getDatabase(application).getNoteDAO()
        repository = Repository(noteDB)
        getAllNote = repository.getAllNotes()
    }

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.newNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch {
                repository.updateNote(note)
            }
        }
    }

    fun databaseSearch(searchQuery:String):LiveData<List<Note>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}

