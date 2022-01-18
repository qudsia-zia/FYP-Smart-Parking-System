package com.example.hyperparkfyp

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.tasks.OnCompleteListener

object MySingleton {
    var myVariable: Int = 0
}

class Login : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MySingleton.myVariable = 0
        val textViewForgotPassword = findViewById<TextView>(R.id.textViewForgotPasssword)
        textViewForgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
            //finish()
        }
    }

    fun startHome(view: View) { //Login Button

        val edtTxtName = findViewById<EditText>(R.id.editTextName)
        val edtTxtPassword = findViewById<EditText>(R.id.editTextPassword)

        val txtError = findViewById<TextView>(R.id.txtError)

        if (edtTxtName.text.toString() != "" && edtTxtPassword.text.toString() != "") {
            if (edtTxtPassword.text.toString().length > 7) {
                loginUser(edtTxtName.text.toString(), edtTxtPassword.text.toString())
            } else {
                txtError.visibility = View.VISIBLE
                txtError.text = "Password Too Short.!"
            }
        } else {
            txtError.visibility = View.VISIBLE
            txtError.text = "Please Enter Credentials!"
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successful (:", Toast.LENGTH_SHORT).show()
                MySingleton.myVariable = 1
                startActivity(Intent(this, Home::class.java))
                this.finish()
            } else {
                val txtError = findViewById<TextView>(R.id.txtError)
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                txtError.visibility = View.VISIBLE
                txtError.text = "Incorrect Credentials!"
            }
        }
    }

    fun startRegisterActivity(view: View) {
        startActivity(Intent(this, Register::class.java))
    }

    fun guestLogin(view: View) {
        startActivity(Intent(this, Home::class.java))
    }
}

//    fun ResetPass(view: View) {
//        val builder=AlertDialog.Builder(this)
//        builder.setTitle("Reset Password")
//        val view=layoutInflater.inflate(R.layout.dialouge_reset_password,null)
//        val username=findViewById<EditText>(R.id.et_username)
//        builder.setView(view)
//        builder.setPositiveButton("Reset",DialogInterface.OnClickListener { _, _ ->  })
//        if(tv_username.text.toString().isEmpty())
//        builder.setNegativeButton("Close",DialogInterface.OnClickListener { _, _ ->  })
//            }
//
//}