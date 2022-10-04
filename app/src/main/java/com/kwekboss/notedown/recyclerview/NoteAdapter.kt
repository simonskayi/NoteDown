package com.kwekboss.notedown.recyclerview

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kwekboss.notedown.R
import com.kwekboss.notedown.model.Note

class NoteAdapter(
    private val noteAdapterInterface: NoteAdapterInterface,
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(layout, noteAdapterInterface)
    }

    // this code will randomly create background colors for the cardview //
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])

        // creating  a reference of colors
        val color = ArrayList<Int>()
        color.add(R.color.color_1)
        color.add(R.color.color_2)
        color.add(R.color.color_3)
        color.add(R.color.color_4)
        color.add(R.color.color_5)
        color.add(R.color.color_7)

        //to provide random colors from the list
        val random = color.random()
        holder.cardview.setCardBackgroundColor(holder.itemView.resources.getColor(random, null))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(
        itemView: View,
        noteInterface: NoteAdapterInterface,
    ) : RecyclerView.ViewHolder(itemView) {
        val cardview = itemView.findViewById<CardView>(R.id.cardview)

        init {
            itemView.setOnClickListener {
                noteInterface.updateNote(differ.currentList[absoluteAdapterPosition])
            }
            itemView.setOnLongClickListener {
                noteInterface.onDeleteNote(differ.currentList[absoluteAdapterPosition])

                return@setOnLongClickListener true
            }
        }

        fun bindView(note: Note) {
            val noteHeading = itemView.findViewById<TextView>(R.id.tv_model)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            noteHeading.text = note.tittle
            date.text = note.date
        }
    }

    interface NoteAdapterInterface {
        fun onDeleteNote(note: Note)
        fun updateNote(note: Note)

    }

}
