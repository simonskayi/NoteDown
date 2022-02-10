package com.kwekboss.notedown

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kwekboss.notedown.recyclerview.NoteAdapter

import com.kwekboss.notedown.viewmodel.NoteDownViewModel

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {

    private lateinit var viewModel: NoteDownViewModel
lateinit var adapter:NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview  = findViewById<RecyclerView>(R.id.recycler_view)
        val newNote = findViewById<FloatingActionButton>(R.id.fab_new_note)
        val search = findViewById<SearchView>(R.id.searchView)
        // Creating an instance of the viewModel
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteDownViewModel::class.java]

        // Handling the recyclerview
       adapter = NoteAdapter()
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

        // this Codes below handles the searchView Implementation
        search?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(searchBar: String?): Boolean {
            if (searchBar != null) {
                searchData(searchBar)}
        return true
    }

    override fun onQueryTextChange(searchBar: String?): Boolean {
        if(searchBar!=null){
             searchData(searchBar)
        }
        return true
    }

@SuppressLint("NotifyDataSetChanged")
private fun searchData(search:String){
    val searchQuery = "%$search%"
    viewModel.databaseSearch(searchQuery).observe(this){
         adapter.displayList(it)
        adapter.notifyDataSetChanged()
    }
}
}