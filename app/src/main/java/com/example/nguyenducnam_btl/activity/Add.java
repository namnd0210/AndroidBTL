package com.example.nguyenducnam_btl.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.adapter.AsiaFoodAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Add extends AppCompatActivity {
    private Integer TodoNum = new Random().nextInt();
    private String keytodo = Integer.toString(TodoNum);

    private EditText etName, etPrice, etDescription;
    private ImageView  btnBack;
    private RatingBar rating;
    private Button btnAdd;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        initView();

        handleSaveBtn();
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

    private void handleSaveBtn() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> todo = new HashMap<>();
                todo.put("name", etName.getText().toString());
                todo.put("price", etPrice.getText().toString());
                todo.put("rating", String.valueOf(rating.getRating()));
                todo.put("imageUrl", "1");
                todo.put("description", etDescription.getText().toString());
                todo.put("key", keytodo);

                // Add a new document with a generated ID
                db.collection("food")
                        .add(todo)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                                System.out.println("OK");

                                Toast.makeText(Add.this,
                                        "OK" + documentReference.getId(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);

                                System.out.println("NG");

                                Toast.makeText(Add.this,
                                        "failed roi" , Toast.LENGTH_SHORT).show();
                            }
                        });

                finish();
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        rating = findViewById(R.id.rating);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setClickable(true);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setClickable(true);
    }
}
