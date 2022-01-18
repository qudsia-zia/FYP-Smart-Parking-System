package com.example.hyperparkfyp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Register : AppCompatActivity() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun register(view: View) {

        val newEmail = findViewById<EditText>(R.id.editTextNewUserName)
        val newUserPassword = findViewById<EditText>(R.id.editTextNewUserPassword)

        val txtError = findViewById<TextView>(R.id.txtError2)

        if ( newEmail.text.toString() != "" && newUserPassword.text.toString() != "") {
            if ( newUserPassword.text.toString().length < 8) {
                txtError.visibility = View.VISIBLE
                txtError.text = "Password is too short!"
            } else {
                registerUser(newEmail.text.toString(), newUserPassword.text.toString())
            }
        } else {
            txtError.visibility = View.VISIBLE
            txtError.text = "Please Enter All Credentials!"
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Logging In (:", Toast.LENGTH_SHORT).show()
                MySingleton.myVariable = 1
                startActivity(Intent(this, Home::class.java))
                this.finish()
            } else {
                Toast.makeText(baseContext, "Authentication failed.\nThe mail is already in use..", Toast.LENGTH_SHORT).show()
            }
        }
    }

}