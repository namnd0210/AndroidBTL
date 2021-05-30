package com.example.nguyenducnam_btl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.adapter.AsiaFoodAdapter;
import com.example.nguyenducnam_btl.adapter.PopularFoodAdapter;
import com.example.nguyenducnam_btl.model.AsiaFood;
import com.example.nguyenducnam_btl.model.PopularFood;
import com.example.nguyenducnam_btl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<AsiaFood> asiaFoodList;
    List<PopularFood> popularFoodList;

    FirebaseFirestore db;
    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    AsiaFoodAdapter asiaFoodAdapter;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    EditText searchField;
    ImageView info;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        navigateAddActivity();
        handleSearch();
        navigateInfoUser();
    }

    private void navigateInfoUser() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Info.class);

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAG_GET_USER", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w("TAG_GET_USER", "Error getting documents.", task.getException());
                                }
                            }
                        });

                startActivity(intent);
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

    void filter(String text){
        List<AsiaFood> temp1 = new ArrayList();
        List<PopularFood> temp2 = new ArrayList();
        for(AsiaFood d: asiaFoodList){
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                temp1.add(d);
            }
        }
        for(PopularFood d: popularFoodList){
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                temp2.add(d);
            }
        }
        //update recyclerview
        asiaFoodAdapter.updateList(temp1);
        popularFoodAdapter.updateList(temp2);
    }

    private void navigateAddActivity() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Add.class);
                startActivity(intent);
            }
        });
    }

    private void setPopularRecycler(List<PopularFood> popularFoodList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<AsiaFood> asiaFoodList) {
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

                                asiaFoodList.add(new AsiaFood(key, name, price, imageUrl, rating, description));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                            asiaRecycler.setLayoutManager(layoutManager);
                            asiaFoodAdapter = new AsiaFoodAdapter(MainActivity.this, asiaFoodList);
                            asiaRecycler.setAdapter(asiaFoodAdapter);
                            asiaFoodAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void initView() {
        fab = findViewById(R.id.mainFab);
        asiaRecycler = findViewById(R.id.asia_recycler);
        popularRecycler = findViewById(R.id.popular_recycler);
        searchField = findViewById(R.id.searchField);
        info = findViewById(R.id.info);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // now here we will add some dummy data to out model class
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
//        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
//        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        asiaFoodList = new ArrayList<>();
        setAsiaRecycler(asiaFoodList);

        popularFoodList = new ArrayList<>();
        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));
        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));

        setPopularRecycler(popularFoodList);
    }
}