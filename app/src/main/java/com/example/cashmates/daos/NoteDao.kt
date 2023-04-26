package com.example.cashmates.daos

import com.example.cashmates.models.Note
import com.example.cashmates.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NoteDao {
    val db = FirebaseFirestore.getInstance()
    val noteCollections = db.collection("notes")
    val auth = Firebase.auth

    @OptIn(DelicateCoroutinesApi::class)
    fun addNote(text3:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val note = Note(text3, user, currentTime)
            noteCollections.document().set(note)
        }

    }
}