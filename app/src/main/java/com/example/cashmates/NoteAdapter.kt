package com.example.cashmates

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashmates.models.Note
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat

class NoteAdapter(options: FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(
    options
) {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val noteText: TextView = itemView.findViewById(R.id.notes)
        val date: TextView = itemView.findViewById(R.id.dates)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Note) {
        holder.noteText.text = model.detail
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateString = simpleDateFormat.format(model.time)
        holder.date.text = dateString.toString()
    }

}