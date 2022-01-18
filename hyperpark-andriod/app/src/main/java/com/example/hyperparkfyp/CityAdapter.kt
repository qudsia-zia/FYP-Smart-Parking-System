package com.example.hyperparkfyp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(val context: Context, private val dataSet: Array<String>, private val images: Array<Int>) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    val ctx = context
    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.txtCityName)
        val imgView: ImageView = view.findViewById<ImageView>(R.id.cityThumb)
        val layout = view.findViewById<ConstraintLayout>(R.id.city_layout)

    }

    private fun RecyclerView.getCurrentPosition() : Int {
        return (this.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_city, viewGroup, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CityViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
        viewHolder.imgView.setImageResource(images[position])

        viewHolder.layout.setOnClickListener{
            val intent = Intent(ctx, SelectMall::class.java)
            intent.putExtra("CityName", viewHolder.textView.text)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}
