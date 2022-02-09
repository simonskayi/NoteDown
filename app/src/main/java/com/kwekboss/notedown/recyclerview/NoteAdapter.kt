package com.kwekboss.notedown.recyclerview

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.notedown.R
import com.kwekboss.notedown.model.Note
import kotlin.random.Random

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

     val myNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(layout)
    }
// this code will randomly create background colors for the cardview //
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(myNotes[position])

    // creating  a reference of colors
        val color = ArrayList<Int>()
        color.add(R.color.color_1)
        color.add(R.color.color_2)
        color.add (R.color.color_3)
        color.add(R.color.color_4)
        color.add(R.color.color_5)
        color.add(R.color.color_7)

    //to provide random colors from the list
        val rand = color.random()
        holder.cardview.setCardBackgroundColor(holder.itemView.resources.getColor(rand,null))
    }

    override fun getItemCount(): Int {
        return myNotes.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
 val cardview = itemView.findViewById<CardView>(R.id.cardview)
        fun bindView(note: Note) {
            val noteHeading = itemView.findViewById<TextView>(R.id.tv_model)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            noteHeading.text = note.tittle
            date.text = note.date

        // onclick delete codes will be here

            // on click see note && update will be here.


        }
    }
    // this function will populate saved notes to the recyclerview
    fun displayList(note: List<Note>){
      myNotes.clear()
        myNotes.addAll(note)
    }


}

