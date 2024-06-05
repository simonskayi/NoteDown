package com.kwekboss.notedown.database


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.model.NoteDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date

/*Test to check if NoteDatabase is working as expected
* This includes the NoteDAO functions
* A custom  extention function has been added to the Livedata to ease testing*/

@RunWith(AndroidJUnit4::class)
internal class NoteDatabaseTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: NoteDownDatabase
    private lateinit var noteDao: NoteDao
    private val calender = Calendar.getInstance().time

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NoteDownDatabase::class.java).build()
        noteDao = database.getNoteDAO()

    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun canWriteToDatabase() = runBlocking {
        val note = Note(2, "Making A Test", "I think we've Passed the Test", calender)
        noteDao.insert(note)
        val allNotes = noteDao.allNotes().getOrAwaitValue()

        assertThat(allNotes.contains(note)).isTrue()
    }

    @Test
    fun ableToDeleteFromDatabase() = runBlocking {

        val note = Note(2, "Test Deletion", "I think we've Passed the Test", calender)
        noteDao.insert(note)

        val deleteNote = noteDao.allNotes().getOrAwaitValue().find {
            it.date == calender
        }

        noteDao.delete(deleteNote!!)

        val confirmDeletion = noteDao.allNotes().getOrAwaitValue()
        assertThat(confirmDeletion.isEmpty()).isTrue()
    }


    @Test
    fun ableToUpdateDatabaseNotes() = runBlocking {
        val note = Note(2, "Update Test", "I think we've Passed the Test", calender)
        noteDao.insert(note)

        val findNote = noteDao.allNotes().getOrAwaitValue().find {
            it.tittle == "Update Test"
        }
        val updatedNote = Note(findNote!!.id, "Test Passed", findNote.noteBody, findNote.date)
        noteDao.update(updatedNote)

        assertThat(findNote.tittle == updatedNote.tittle).isFalse()
    }


}
