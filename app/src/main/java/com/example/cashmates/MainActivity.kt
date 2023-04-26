package com.example.cashmates


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmates.daos.PostDao
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private companion object{
        private const val TAG = "MainActivity"
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
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

        addNote.setOnClickListener {
            val intent2 = Intent(this, Notes::class.java)
            startActivity(intent2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.miLogout){
            Log.i(TAG,"Logout")
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this,gso)
            googleSignInClient.signOut()
            auth.signOut()
            val logoutIntent = Intent(this,SignInActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}