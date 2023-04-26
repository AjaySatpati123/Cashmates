package com.example.cashmates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashmates.daos.NoteDao
import com.example.cashmates.models.Note
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_notes.*

class Notes : AppCompatActivity() {
    private lateinit var noteDao: NoteDao
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        fab.setOnClickListener {
            val intent3 = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent3)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        noteDao = NoteDao()
        val notesCollections = noteDao.noteCollections
        val query = notesCollections.orderBy("time", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()

        adapter= NoteAdapter(recyclerViewOptions)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = null
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}