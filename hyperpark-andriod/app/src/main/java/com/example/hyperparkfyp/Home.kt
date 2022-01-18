package com.example.hyperparkfyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val buttonval = findViewById<Button>(R.id.buttonBook)
        if(MySingleton.myVariable !=0){
            buttonval.visibility = View.VISIBLE
        }
    }
    fun selectCity(view: View) {
        startActivity(Intent(this, SelectCity()::class.java))
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        MySingleton.myVariable = 0
        startActivity(Intent(this, Login::class.java))
        this.finish()
    }

    fun bookParking(view: View) {
        startActivity(Intent(this, reservation()::class.java))
    }
}