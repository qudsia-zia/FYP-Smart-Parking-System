package com.example.hyperparkfyp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectMall : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_mall)

        val cityName = intent.getStringExtra("CityName")
        var mallNames = when (cityName) {
            "Faisalabad" -> {
                resources.getStringArray(R.array.fsd_malls)
            }
            "Lahore" -> {
                resources.getStringArray(R.array.lhr_malls)
            }
            else -> {
                resources.getStringArray(R.array.isb_malls)
            }
        }

        var thumbs = when (cityName) {
            "Faisalabad" -> {
                arrayOf(R.drawable.imtiaz, R.drawable.galleria, R.drawable.chase)
            }
            "Lahore" -> {
                arrayOf(R.drawable.emporium, R.drawable.packages, R.drawable.pace)
            }
            else -> {
                arrayOf(R.drawable.centaurus, R.drawable.giga, R.drawable.mall)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.mall_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MallAdapter(this, mallNames, thumbs)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.itemOne -> {
                startActivity(Intent(this, SelectCity::class.java))
//                Toast.makeText(this,"", Toast.LENGTH_LONG).show()
                true
            }
            R.id.itemTwo -> {
                startActivity(Intent(this, contact::class.java))
//                Toast.makeText(this,"", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}}