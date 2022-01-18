package com.example.hyperparkfyp

import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class ParkingSlots : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("/Faisalabad/Imtiaz Mart/ParkingSlots")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_slots)

        Toast.makeText(applicationContext, "Loading Data.. Please Wait..", Toast.LENGTH_SHORT).show()

        findViewById<TextView>(R.id.textViewMallName).text = intent.getStringExtra("MallName")+": Parking Area"

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gridView = findViewById<GridView>(R.id.grid_view_parking_slots)
                var parkingSlots = arrayListOf<ParkingSlot>()

                for (i in dataSnapshot.children) {
                    parkingSlots.add(
                        ParkingSlot(
                            i.key.toString(),
                            i.children.last().child("Status").value.toString(),
                            i.children.last().child("Entry_time").value.toString()
                        ))
                }
                gridView.adapter = ParkingSlotAdapter(applicationContext, R.layout.grid_cell, parkingSlots)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}