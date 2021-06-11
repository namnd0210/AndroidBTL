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
import com.example.nguyenducnam_btl.activity.Update;
import com.example.nguyenducnam_btl.model.Food;

import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.AsiaFoodViewHolder> {
    Context context;
    List<Food> list;

    public FoodAdapter(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.asia_food_row_item, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsiaFoodViewHolder holder, int position) {

//        holder.foodImage.setImageResource(list.get(position).getImageUrl());
        holder.foodImage.setImageResource(R.drawable.asiafood1);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("$" + list.get(position).getPrice());
        holder.rating.setText(String.valueOf(list.get(position).getRating()));
        holder.description.setText(list.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        Update.class);

                System.out.println(list.get(position).toString());

                intent.putExtra("key", list.get(position).getKey());
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("price", list.get(position).getPrice());
//                intent.putExtra("imageUrl", list.get(position).getImageUrl());
                intent.putExtra("imageUrl", R.drawable.asiafood1);
                intent.putExtra("rating", list.get(position).getRating());
                intent.putExtra("description", list.get(position).getDescription());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<Food> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder {


        ImageView foodImage;
        TextView price, name, rating, description;

        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            description = itemView.findViewById(R.id.description);
        }
    }

}
