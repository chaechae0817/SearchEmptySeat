package com.example.myapplication;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private ArrayList<StoreData> storeData = new ArrayList<>();
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.store_menu_name);
            price = itemView.findViewById(R.id.menu_price);
        }

    }
    public StoreAdapter (ArrayList<StoreData> dataset) {
        storeData = dataset;
    }
    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item, parent, false);
        StoreAdapter.ViewHolder viewHolder = new StoreAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        StoreData store = storeData.get(position);
        holder.title.setText(store.getTitle());
        holder.price.setText(store.getPrice());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return storeData.size();
    }
}
