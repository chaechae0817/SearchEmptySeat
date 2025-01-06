package com.example.myapplication;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<FavoriteData> favoriteData = new ArrayList<>();
    public interface OnItemClickListener {
        void onItemClicked(int position, String title);
    }

    // OnItemClickListener 참조 변수 선언
    private FavoriteAdapter.OnItemClickListener itemClickListener;
    public void setOnItemClickListener (FavoriteAdapter.OnItemClickListener listener) {
        itemClickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favorite_title);
            image = itemView.findViewById(R.id.favorite_image);

            image.setBackground(new ShapeDrawable(new OvalShape()));
            image.setClipToOutline(true);
        }

    }
    public FavoriteAdapter (ArrayList<FavoriteData> dataset) {
        favoriteData = dataset;
    }

    // OnItemClickListener 전달 메소드

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.v("Sucess","onCreateViewHolder 작동");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_list, parent, false);
        FavoriteAdapter.ViewHolder viewHolder = new FavoriteAdapter.ViewHolder(view);



        //===== [Click 이벤트 구현을 위해 추가된 코드] =====================
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "";
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    title = favoriteData.get(position).getTitle().toString();
                }
                itemClickListener.onItemClicked(position,title);
            }
        });
        //==================================================================







        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteData favorite = favoriteData.get(position);
        Glide.with(holder.itemView.getContext()).load(favorite.getUrl()).into(holder.image);
        holder.title.setText(favorite.getTitle());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return favoriteData.size();
    }
}
