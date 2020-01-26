package com.example.realestatecatalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView text_address;
    ImageView image_property;
    TextView text_area;
    TextView text_price;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        text_address = itemView.findViewById(R.id.text_address);
        image_property = itemView.findViewById(R.id.image_property);
        text_area = itemView.findViewById(R.id.text_area);
        text_price = itemView.findViewById(R.id.text_price);


    }
}
