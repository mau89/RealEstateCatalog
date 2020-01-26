package com.example.realestatecatalog;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatecatalog.data.PropertyAdd;
import com.example.realestatecatalog.data.PropertyDbHolder;
import com.squareup.picasso.Picasso;


import java.util.List;

public class PropertyAddAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Object> items;

    private OnClickProperty onClickProperty;

    public PropertyAddAdapter(List<Object> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);
        holder.text_address.setText(((PropertyAdd) item).getItemTextAddress());

        Uri selectedImage = Uri.parse(((PropertyAdd) item).getItemImageProperty());

        Picasso.get().load(selectedImage).into(holder.image_property);
        holder.text_area.setText(((PropertyAdd) item).getItemTextArea());
        holder.text_price.setText(((PropertyAdd) item).getItemTextPrice());
        holder.itemView.setOnClickListener(v -> onClickProperty.onClickProperty(item));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickProperty(OnClickProperty onClickProperty) {
        this.onClickProperty = onClickProperty;
    }

    public interface OnClickProperty {
        void onClickProperty(Object objects);
    }
}