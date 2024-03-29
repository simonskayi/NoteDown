package com.kwekboss.notedown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.viewmodel.NoteDownViewModel
import java.text.DateFormat
import java.util.*

  class NoteActivity : AppCompatActivity() {
    lateinit var edtxtNoteHead: EditText
    lateinit var edtxtnoteBody: EditText
    private lateinit var viewModel: NoteDownViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        // Finding views by their IDs
        edtxtNoteHead = findViewById(R.id.edtv_note_head)
        edtxtnoteBody = findViewById(R.id.edtv_note_body)
        val saveNote = findViewById<FloatingActionButton>(R.id.fab_save_note)

        //Creating a viewModel instance
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteDownViewModel::class.java]

        // Generating Date for when Note was created
        val calender = Calendar.getInstance()
        val simpleDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calender.time)

        val dataPassed = intent.getStringExtra("updateInput")

        if (dataPassed == "DataIsAvailable") {
            val noteHead = intent.getStringExtra("noteTittle")
            val noteBody = intent.getStringExtra("noteBody")
            edtxtNoteHead.setText(noteHead)
            edtxtnoteBody.setText(noteBody)
        }

        //Wiring the Save Button to (save notes)
        saveNote.setOnClickListener {
            val noteHeader = edtxtNoteHead.text.toString()
            val noteDescription = edtxtnoteBody.text.toString()

            //Updating a Note
            if (dataPassed == "DataIsAvailable") {
                val oldNoteId = intent.getIntExtra("noteId", -1)
                if (noteHeader.isNotEmpty() && noteDescription.isNotEmpty()) {
                    viewModel.updateNote(
                        Note(
                            oldNoteId,
                            noteHeader,
                            noteDescription,
                            simpleDateFormat
                        )
                    )
                    Toast.makeText(this, R.string.note_update, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            else if (noteHeader.isNotEmpty() && noteDescription.isNotEmpty()) {
                viewModel.addNote(Note(0, noteHeader, noteDescription, simpleDateFormat))
                Toast.makeText(this, R.string.toast_messg_sucess, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Snackbar.make(it, R.string.toast_messg_error, Snackbar.LENGTH_LONG).show()
            }

        }

    }
}