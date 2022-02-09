package com.kwekboss.notedown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.viewmodel.NoteDownViewModel
import java.text.DateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteDownViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
         // Finding views by their IDs

        val saveNote = findViewById<FloatingActionButton>(R.id.fab_save_note)
        //Creating a viewModel instance
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteDownViewModel::class.java]

        // Generating Date And Time
        val calender=Calendar.getInstance()
        val simpleDateFormat = DateFormat.getDateInstance(DateFormat.SHORT).format(calender.time)

        //Wiring the Save Button to (save notes)
        saveNote.setOnClickListener {
            val noteHead = findViewById<EditText>(R.id.edtv_note_head).text.toString()
            val noteBody = findViewById<EditText>(R.id.edtv_note_body).text.toString()
            // To Check if the user has typed some notes.
            if (noteHead.isNotEmpty() && noteBody.isNotEmpty()) {
                viewModel.addNote(Note(0, noteHead, noteBody,simpleDateFormat))
                Toast.makeText(this, R.string.toast_messg_sucess, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, R.string.toast_messg_error, Toast.LENGTH_SHORT).show()
            }

        }

    }
}