package com.example.cashmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashmates.daos.PostDao
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
        }
    }
}