package com.kwekboss.notedown

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kwekboss.notedown.model.Note
import com.kwekboss.notedown.recyclerview.NoteAdapter
import com.kwekboss.notedown.utils.Constant

import com.kwekboss.notedown.viewmodel.NoteDownViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    NoteAdapter.NoteAdapterInterface {

    private lateinit var viewModel: NoteDownViewModel
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        val newNote = findViewById<FloatingActionButton>(R.id.fab_new_note)
        val search = findViewById<SearchView>(R.id.searchView)
        // Creating an instance of the viewModel
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteDownViewModel::class.java]

        // Handling the recyclerview
        adapter = NoteAdapter(this)
        recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = adapter

        // Calling all saved notes from the database to the recyclerView
        viewModel.getAllNote.observe(this) {
            adapter.differ.submitList(it)

        }
        // this will start the next activity
        newNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
        // These Codes below handles the searchView Implementation
        search?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(searchBar: String?): Boolean {
        if (searchBar != null) {
            searchData(searchBar)
        }
        return true
    }

    override fun onQueryTextChange(searchBar: String?): Boolean {
        if (searchBar != null) {
            searchData(searchBar)
        }
        return true
    }


    private fun searchData(search: String) {
        val searchQuery = "%$search%"
        viewModel.databaseSearch(searchQuery).observe(this) {
            adapter.differ.submitList(it)
        }
    }

    // this deletes a note from the database from a longClick
    override fun onDeleteNote(note: Note) {
        deleteNoteDialog(this,note)

    }

    // This function sends data to the next activity
    override fun viewOrUpadateNote(note: Note) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(Constant.viewNoteKey, Constant.dataTransfered)
        intent.putExtra(Constant.noteIdKey, note.id)
        intent.putExtra(Constant.noteTitleKey, note.tittle)
        intent.putExtra(Constant.noteBodyKey, note.noteBody)
        startActivity(intent)

    }


    private fun deleteNoteDialog(context:Context, note: Note){
        val dialog = Dialog(context)
        dialog.apply {
            setCancelable(false)
            setContentView(R.layout.custom_dialog_layout)

            val dialogYes = dialog.findViewById<TextView>(R.id.txt_yes)
            val dialogNo  = dialog.findViewById<TextView>(R.id.txt_no)

            dialogYes.setOnClickListener {
               viewModel.deleteNote(note)
                dialog.dismiss()
                Toast.makeText(this@MainActivity, R.string.delete_note, Toast.LENGTH_SHORT).show()
            }

            dialogNo.setOnClickListener {
                dialog.dismiss()
                Toast.makeText(this@MainActivity, R.string.no_deletion, Toast.LENGTH_SHORT).show()
            }
        }.show()

    }
}