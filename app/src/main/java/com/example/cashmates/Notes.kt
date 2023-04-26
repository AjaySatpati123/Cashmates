package com.example.cashmates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notes.*

class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        fab.setOnClickListener {
            val intent3 = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent3)
        }
    }
}