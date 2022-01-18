package com.example.hyperparkfyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectCity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        val thumbs = arrayOf(R.drawable.fsd, R.drawable.lhr, R.drawable.isbd)
        val cities = resources.getStringArray(R.array.cities)

        val recyclerView = findViewById<RecyclerView>(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CityAdapter(this, cities, thumbs)

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