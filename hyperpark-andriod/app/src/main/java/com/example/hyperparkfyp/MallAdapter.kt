package com.example.hyperparkfyp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MallAdapter(val context: Context, private val dataSet: Array<String>, private val images: Array<Int>) :
    RecyclerView.Adapter<MallAdapter.MallViewHolder>() {

    val ctx = context

    class MallViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.txtMallName)
        val imgView: ImageView = view.findViewById<ImageView>(R.id.mallThumb)
        val layout = view.findViewById<ConstraintLayout>(R.id.mall_layout)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MallViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_mall, viewGroup, false)
        return MallViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MallViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
        viewHolder.imgView.setImageResource(images[position])

        viewHolder.layout.setOnClickListener{
            val intent = Intent(ctx, ParkingSlots::class.java)
            intent.putExtra("MallName", viewHolder.textView.text)
            ctx.startActivity(intent)
        }

    }

    override fun getItemCount() = dataSet.size
}
