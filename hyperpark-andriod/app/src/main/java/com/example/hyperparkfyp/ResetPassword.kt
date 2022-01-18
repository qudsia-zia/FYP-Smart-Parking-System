package com.example.hyperparkfyp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        val buttonReset = findViewById<Button>(R.id.buttonResetPass)
        buttonReset.setOnClickListener {
//            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            val editTextEmail = findViewById<EditText>(R.id.editPassEmail)
            val textViewResponse = findViewById<TextView>(R.id.textViewalert)
            if (editTextEmail.text.toString().isNullOrEmpty())
                textViewResponse.text = "Email Address is not provided"
            else {
                auth.sendPasswordResetEmail(
                    editTextEmail.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            textViewResponse.text = "Reset Password Link is sent."
                        } else
                            textViewResponse.text = "Password Reset mail could not be sent"
                    }
            }
        }

        }
    }