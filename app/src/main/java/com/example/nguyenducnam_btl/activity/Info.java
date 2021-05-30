package com.example.nguyenducnam_btl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenducnam_btl.R;
import com.google.firebase.auth.FirebaseAuth;

public class Info extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvName, tvDob, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
        setIntentValue();
        handleBackBtn();
    }

    private void setIntentValue() {
        tvName.setText(getIntent().getStringExtra("name"));
        tvDob.setText(getIntent().getStringExtra("dob"));
        tvEmail.setText(getIntent().getStringExtra("email"));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    private void handleBackBtn() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setClickable(true);

        tvName = findViewById(R.id.tvName);
        tvDob = findViewById(R.id.tvDob);
        tvEmail = findViewById(R.id.tvEmail);
    }

}