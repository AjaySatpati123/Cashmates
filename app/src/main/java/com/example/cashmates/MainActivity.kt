package com.example.cashmates


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmates.daos.PostDao
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add.setOnClickListener {
            val input1 = enterAmount.text.toString().trim()
            val input2 = spendLocation.text.toString().trim()
            if(input1.isNotEmpty() && input2.isNotEmpty()){
                val postDao = PostDao()
                postDao.addPost(input1,input2)
            }
            MaterialAlertDialogBuilder(this)
                .setMessage("Added Successfully")
                .setPositiveButton("Ok") { _, _ ->
                    Snackbar.make(rootLayout,"Happy Saving", Snackbar.LENGTH_SHORT).show()
                }.show()
        }

        analyze.setOnClickListener {
            val intent = Intent(this, Analyze::class.java)
            startActivity(intent)
        }
    }
}