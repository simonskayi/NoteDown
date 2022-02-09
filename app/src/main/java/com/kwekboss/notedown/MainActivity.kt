package com.kwekboss.notedown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kwekboss.notedown.recyclerview.NoteAdapter

import com.kwekboss.notedown.viewmodel.NoteDownViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteDownViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview  = findViewById<RecyclerView>(R.id.recycler_view)
        val newNote = findViewById<FloatingActionButton>(R.id.fab_new_note)
        // Creating an instance of the viewModel
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteDownViewModel::class.java]

        // Handling the recyclerview
        val adapter = NoteAdapter()
        recyclerview.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            recyclerview.adapter = adapter

      // Calling all saved notes from the database to the recyclerView
      viewModel.getAllNote.observe(this) {
         adapter.displayList(it)
          adapter.notifyItemChanged(it.size-1)

      }
        // this will start the next activity
        newNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

    }

}