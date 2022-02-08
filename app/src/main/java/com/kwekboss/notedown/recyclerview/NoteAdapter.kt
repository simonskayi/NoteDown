package com.kwekboss.notedown.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.notedown.R
import com.kwekboss.notedown.model.Note

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

     val myNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(myNotes[position])
    }

    override fun getItemCount(): Int {
        return myNotes.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(note: Note) {
            val noteHeading = itemView.findViewById<TextView>(R.id.tv_model)
            noteHeading.text = note.tittle

        // onclick delete codes will be here
        }
    }
    // this function will populate saved notes to the recyclerview
    fun displayList(note: List<Note>){
      myNotes.clear()
        myNotes.addAll(note)
    }
}

