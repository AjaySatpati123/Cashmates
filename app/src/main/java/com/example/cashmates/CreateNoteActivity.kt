package com.example.cashmates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmates.daos.NoteDao
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        noteButton.setOnClickListener {
            val input = noteInput.text.toString().trim()
            if(input.isNotEmpty()){
                val noteDao = NoteDao()
                noteDao.addNote(input)
                finish()
            }
        }
    }
}