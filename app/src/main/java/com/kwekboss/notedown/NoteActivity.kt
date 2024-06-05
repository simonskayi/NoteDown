package com.kwekboss.notedown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.utils.Constant
import com.kwekboss.notedown.viewmodel.NoteDownViewModel
import java.util.*

  class NoteActivity : AppCompatActivity() {
    private lateinit var edtxtNoteHead: EditText
    private lateinit var edtxtnoteBody: EditText
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
        val currentDate = Calendar.getInstance().time

        // Previewing Data
        val dataPassed = intent.getStringExtra(Constant.viewNoteKey)
        if (dataPassed == Constant.dataTransfered) {
            val noteHead = intent.getStringExtra(Constant.noteTitleKey)
            val noteBody = intent.getStringExtra(Constant.noteBodyKey)
            edtxtNoteHead.setText(noteHead)
            edtxtnoteBody.setText(noteBody)
        }

        //Wiring the Save Button to (save notes)
        saveNote.setOnClickListener {
            val noteHeader = edtxtNoteHead.text.toString()
            val noteDescription = edtxtnoteBody.text.toString()

            //Updating a Note
            if (dataPassed == Constant.dataTransfered) {
                val oldNoteId = intent.getIntExtra(Constant.noteIdKey, -1)
                if (noteHeader.isNotEmpty() && noteDescription.isNotEmpty()) {
                    viewModel.updateNote(
                        Note(
                            oldNoteId,
                            noteHeader,
                            noteDescription,
                            currentDate
                        )
                    )
                    Toast.makeText(this, R.string.note_update, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            else if (noteHeader.isNotEmpty() && noteDescription.isNotEmpty()) {
                viewModel.addNote(Note(0, noteHeader, noteDescription, currentDate))
                Toast.makeText(this, R.string.toast_messg_sucess, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Snackbar.make(it, R.string.toast_messg_error, Snackbar.LENGTH_LONG).show()
            }

        }

    }
}