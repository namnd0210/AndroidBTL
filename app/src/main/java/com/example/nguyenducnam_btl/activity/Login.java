package com.example.nguyenducnam_btl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nguyenducnam_btl.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth fAuth;
    private Button btLogin, btRegister;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        initView();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (email.isEmpty()) {
                    etEmail.setError("Email is not blank");
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password is not blank");
                    return;
                }
                if (password.length() < 6) {
                    etPassword.setError("Password must be >= 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this,
                                        MainActivity.class);

                                System.out.println("Login success");

                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Login Failed");

                        Toast.makeText(Login.this,
                                "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,
                        Register.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        etEmail = findViewById(R.id.txtEmail);
        etPassword = findViewById(R.id.txtPass);
    }
}
