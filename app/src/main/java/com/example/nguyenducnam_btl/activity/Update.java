package com.example.nguyenducnam_btl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.nguyenducnam_btl.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    private EditText etName, etPrice, etDate, etDescription;
    private Button btnDelete, btnUpdate;
    private ImageView btnBack;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        initView();
        setIntentValue();

        handleUpdateBtn();
        handleDeleteBtn();
        handleBackBtn();
    }

    private void handleBackBtn() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleDeleteBtn() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("food")
                        .document(getIntent().getStringExtra("key"))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });

                finish();
            }
        });
    }

    private void handleUpdateBtn() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("food")
                        .document(getIntent().getStringExtra("key"));
                System.out.println(getIntent().getStringArrayExtra("key"));
                Map<String, Object> food = new HashMap<>();
                food.put("name", etName.getText().toString());
                food.put("price", etPrice.getText().toString());
                food.put("rating", String.valueOf(rating.getRating()));
                food.put("imageUrl", "1");
                food.put("description", etDescription.getText().toString());

                ref.set(food);

                finish();
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDate = findViewById(R.id.etDate);
        rating = findViewById(R.id.rating);
        etDescription = findViewById(R.id.etDescription);
        btnBack = findViewById(R.id.btnBack);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void setIntentValue() {
        etName.setText(getIntent().getStringExtra("name"));
        etPrice.setText(getIntent().getStringExtra("price"));
//        etDate.setText(getIntent().getStringExtra("imageUrl")); //fix this
        etDate.setText("123"); //fix this
        rating.setRating(Float.parseFloat(getIntent().getStringExtra("rating") + ""));
        etDescription.setText(getIntent().getStringExtra("description"));
    }
}