package com.example.nguyenducnam_btl.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.activity.Info;
import com.example.nguyenducnam_btl.adapter.FoodAdapter;
import com.example.nguyenducnam_btl.adapter.PopularFoodAdapter;
import com.example.nguyenducnam_btl.model.Food;
import com.example.nguyenducnam_btl.model.PopularFood;
import com.example.nguyenducnam_btl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    List<Food> foodList;
    List<PopularFood> popularFoodList;

    FirebaseFirestore db;
    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    FoodAdapter asiaFoodAdapter;
    EditText searchField;
    ImageView info;
    TextView total;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        initView(v);

        handleSearch();
        navigateInfoUser();

        return v;
    }

    private void navigateInfoUser() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        Info.class);

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println(currentFirebaseUser.getDisplayName()+" "+ currentFirebaseUser.getEmail());

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("userUid").equals(currentFirebaseUser.getUid())) {
                                            String name = document.getString("name");
                                            String email = document.getString("email");
                                            String dob = document.getString("dob");

                                            intent.putExtra("name", name);
                                            intent.putExtra("email", email);
                                            intent.putExtra("dob", dob);
                                            startActivity(intent);
                                            return;
                                        }
                                        Log.d("TAG_GET_USER", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w("TAG_GET_USER", "Error getting documents.", task.getException());
                                }
                            }
                        });


            }
        });
    }

    private void handleSearch() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }

    void filter(String text) {
        List<Food> temp1 = new ArrayList();
        List<PopularFood> temp2 = new ArrayList();
        for (Food d : foodList) {
            if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                temp1.add(d);
            }
        }
        for (PopularFood d : popularFoodList) {
            if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                temp2.add(d);
            }
        }
        //update recyclerview
        asiaFoodAdapter.updateList(temp1);
        popularFoodAdapter.updateList(temp2);
    }

    private void setPopularRecycler(List<PopularFood> popularFoodList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(getContext(), popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<Food> foodList) {
        db = FirebaseFirestore.getInstance();

        db.collection("food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String key = document.getId();
                                String name = document.getString("name");
                                String price = document.getString("price");
                                Integer imageUrl = Integer.parseInt(document.getString("imageUrl"));
                                String rating = document.getString("rating");
                                String description = document.getString("description");

                                System.out.println(description);

                                foodList.add(new Food(key, name, price, imageUrl, rating, description));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            asiaRecycler.setLayoutManager(layoutManager);
                            asiaFoodAdapter = new FoodAdapter(getContext(), foodList);
                            asiaRecycler.setAdapter(asiaFoodAdapter);
                            total.setText("Total items: " + foodList.size());
                            asiaFoodAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void initView(View v) {
        asiaRecycler = v.findViewById(R.id.asia_recycler);
        popularRecycler = v.findViewById(R.id.popular_recycler);
        searchField = v.findViewById(R.id.searchField);
        info = v.findViewById(R.id.info);
        total = v.findViewById(R.id.total);
    }


    @Override
    public void onResume() {
        super.onResume();
        // now here we will add some dummy data to out model class
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        foodList = new ArrayList<>();
        setAsiaRecycler(foodList);

        popularFoodList = new ArrayList<>();
        popularFoodList.add(new PopularFood("Float Cake Vietnam 1", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick 1", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick 2", "$25.05", R.drawable.popularfood3));
        popularFoodList.add(new PopularFood("Float Cake Vietnam 2", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick 3", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick 3", "$25.05", R.drawable.popularfood3));

        setPopularRecycler(popularFoodList);

    }
}