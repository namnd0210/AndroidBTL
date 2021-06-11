package com.example.nguyenducnam_btl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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