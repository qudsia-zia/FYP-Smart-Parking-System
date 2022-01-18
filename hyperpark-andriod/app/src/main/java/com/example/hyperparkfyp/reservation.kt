package com.example.hyperparkfyp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import com.google.firebase.database.DatabaseReference




class reservation : AppCompatActivity() {
    var parkingSpaces = ArrayList<ParkingSlot>()
    val database = FirebaseDatabase.getInstance()

    var myRef = database.getReference("Faisalabad/Imtiaz Mart/ParkingSlots")

    var instance = ""

    var available = ""
    //var loc= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children) {

                    if(i.children.last().child("Status").value.toString() == "Available") {
                        instance = "Faisalabad/Imtiaz Mart/ParkingSlots/" + i.key.toString() + "/" + i.children.last().key.toString() + "/Status"
                        //Toast.makeText(applicationContext, i.children.last().child("Status").value.toString(), Toast.LENGTH_SHORT).show()
                        break
                    } else {
                        available = "No free slots available for booking"
                    }
//                    Toast.makeText(applicationContext, instance, Toast.LENGTH_SHORT).show()

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }

    fun book(view: View) {
        val editTextResponse = findViewById<EditText>(R.id.editvehicleid)
        val editTextCityid = findViewById<EditText>(R.id.editcityid)
        val editTextMallid = findViewById<EditText>(R.id.editmallid)
        val errorResponse = findViewById<TextView>(R.id.textViewErrorid)
        if(editTextResponse.text.toString() == ""){
            errorResponse.text = "Enter Data in all fields!"
        }
        else if (editTextCityid.text.toString() != "Faisalabad" && editTextCityid.text.toString() != "Islamabad" && editTextCityid.text.toString() != "Lahore"){
            errorResponse.text = "City not registered!"
        }
        else if(editTextMallid.text.toString() != "Imtiaz Mart" && editTextMallid.text.toString() != "Chase Up" && editTextMallid.text.toString() != "Lyallpur Galleria" && editTextMallid.text.toString() != "Emporium" && editTextMallid.text.toString() != "Packages Mall" && editTextMallid.text.toString() != "Pace Mall" && editTextMallid.text.toString() != "Centaurus" && editTextMallid.text.toString() != "Giga Mall" && editTextMallid.text.toString() != "Mall of Islamabad"){
            errorResponse.text = "Mall not registered!"
        }
        else{
        if (instance != "") {
                var myRef = database.getReference(instance)
                myRef.setValue("Booked")

                startActivity(Intent(this, ParkingSlots::class.java))
                Toast.makeText(this, "Booking Done (:", Toast.LENGTH_SHORT).show()
            instance =""
            }
        else if (available != ""){
            Toast.makeText(this, available, Toast.LENGTH_SHORT).show()
        }
    }
        }
}