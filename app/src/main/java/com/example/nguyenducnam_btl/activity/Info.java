package com.example.nguyenducnam_btl.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenducnam_btl.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Info extends AppCompatActivity {
    public Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageRef;

    private ImageView btnBack, btnEdit, profileImg;
    private TextView tvName, tvDob, tvEmail;
    private String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
        setIntentValue();
        handleBackBtn();
        handleChangeProfileImg();
        downloadFile();
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    profileImg.setImageURI(uri);
                    imageUri = uri;
                    uploadFile();
                }
            });

    private void handleChangeProfileImg() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                mGetContent.launch("image/*");
                uploadFile();
            }
        });
    }

    private void downloadFile() {
        try {
            StorageReference ref = storageRef.child("images/" + userUid);

            final long ONE_MEGABYTE = 1024 * 1024;
            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);

                    profileImg.setMinimumHeight(dm.heightPixels);
                    profileImg.setMinimumWidth(dm.widthPixels);
                    profileImg.setMaxHeight(1024);
                    profileImg.setMaxWidth(1024);

                    profileImg.setImageBitmap(bm);
                    Toast.makeText(getBaseContext(), "Download image success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getBaseContext(), "Download image success", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadFile() {
       try {
           StorageReference mountainsRef = storageRef.child("images/" + userUid);

           mountainsRef.putFile(imageUri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Toast.makeText(getBaseContext(), "Upload image success", Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getBaseContext(), "Upload image failed", Toast.LENGTH_SHORT).show();
                       }
                   });
       } catch (Exception e) {
           e.printStackTrace();
       }
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
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setClickable(true);

        tvName = findViewById(R.id.tvName);
        tvDob = findViewById(R.id.tvDob);
        tvEmail = findViewById(R.id.tvEmail);
        profileImg = findViewById(R.id.profileImg);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        userUid = getIntent().getStringExtra("userUid");
    }

}