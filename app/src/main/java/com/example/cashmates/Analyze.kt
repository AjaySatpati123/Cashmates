package com.example.cashmates

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Analyze : AppCompatActivity() {
    private val db = Firebase.firestore
    private val myCollectionRef = db.collection("posts")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        readData(object: MyCallback {
            override fun onCallback(value: HashMap<String,Int>) {
                Log.d("TAG", value.size.toString())
            }
        })
    }
    private fun readData(myCallback : MyCallback) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        myCollectionRef.whereEqualTo("createdBy.uid", userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val hashMap : HashMap<String, Int> = HashMap()
                for (document in task.result) {
                    val amount = document.getString("amount")?.let { Integer.parseInt(it) }
                    val locate = document.getString("locate").toString()
                    if (hashMap.containsKey(locate)) {
                        val count = hashMap[locate] ?: 0
                        hashMap[locate] = count + amount!!
                    } else {
                        hashMap[locate] = amount!!
                    }
                }
                myCallback.onCallback(hashMap)
            }
        }
    }
    interface MyCallback {
        fun onCallback(value: HashMap<String,Int>)
    }
}