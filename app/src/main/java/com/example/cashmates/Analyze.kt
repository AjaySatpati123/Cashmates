package com.example.cashmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Analyze : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}