package com.example.hyperparkfyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        // fun login(view: View) {
        //   startActivity(Intent(this, Login::class.java))
        //}

    }
    }
