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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapater extends RecyclerView.Adapter<SearchAdapater.ViewHolder>{
    private ArrayList<SearchData> searchData = new ArrayList<>();
    SearchAdapater searchD;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView ranking;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.searchName);
            ranking = itemView.findViewById(R.id.SearchRanking);

        }

    }
    public SearchAdapater (ArrayList<SearchData> dataset) {
        searchData = dataset;
    }



    @NonNull
    @Override  // ViewHolder 객체를 생성하여 리턴한다.
    public SearchAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item_list, parent, false);
        SearchAdapater.ViewHolder viewHolder = new SearchAdapater.ViewHolder(view);


        return viewHolder;
    }

    @Override   // ViewHolder안의 내용을 position에 해당되는 데이터로 교체한다.
    public void onBindViewHolder(@NonNull SearchAdapater.ViewHolder holder, int position) {
        SearchData sd = searchData.get(position);
        holder.title.setText(sd.getTitle());
            holder.ranking.setText(""+sd.getRanking());
        holder.itemView.setTag(position);
    }

    @Override   //전체 데이터의 개수를 리턴
    public int getItemCount() {
        return searchData.size();
    }
}
