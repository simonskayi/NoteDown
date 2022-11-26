package com.kwekboss.notedown.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_dataBase ORDER BY id DESC")
    fun allNotes():LiveData<List<Note>>

    @Query("SELECT * FROM note_dataBase WHERE tittle LIKE :searchQuery")
    fun searchQuery(searchQuery: String): Flow<List<Note>>
}