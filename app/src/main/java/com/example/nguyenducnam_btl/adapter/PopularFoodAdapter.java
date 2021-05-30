package com.example.nguyenducnam_btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.activity.Detail;
import com.example.nguyenducnam_btl.model.AsiaFood;
import com.example.nguyenducnam_btl.model.PopularFood;

import java.util.List;

import static android.view.View.OnClickListener;


public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {
    Context context;
    List<PopularFood> list;

    public PopularFoodAdapter(Context context, List<PopularFood> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.popular_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {

        holder.foodImage.setImageResource(list.get(position).getImageUrl());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Detail.class);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);



        }
    }

    public void updateList(List<PopularFood> list){
        this.list = list;
        notifyDataSetChanged();
    }

}
