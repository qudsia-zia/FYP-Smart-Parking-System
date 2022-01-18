package com.example.hyperparkfyp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ParkingSlotAdapter extends ArrayAdapter {

    ArrayList<ParkingSlot> parkingSpaces = new ArrayList<ParkingSlot>();

    public ParkingSlotAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        parkingSpaces = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_cell, null);

        TextView textView = (TextView) v.findViewById(R.id.tv_grid_parking_slot_name);
        TextView tvTimeStamp = (TextView) v.findViewById(R.id.txtTimeStamp);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageViewGridItem);

        if (parkingSpaces.get(position).getStatus().equals("Not Available")){
            imageView.setImageResource(R.drawable.car);
        } else if (parkingSpaces.get(position).getStatus().equals("Booked")){
            imageView.setImageResource(R.drawable.car_booked);
        } else {
            imageView.setImageResource(R.drawable.ic_parking_24);
        }

        tvTimeStamp.setText(parkingSpaces.get(position).getTime());
        textView.setText(parkingSpaces.get(position).getName());

        return v;
    }

}
