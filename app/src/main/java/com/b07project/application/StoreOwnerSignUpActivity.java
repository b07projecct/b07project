package com.b07project.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class StoreOwnerSignUpActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextBrand;
    Button SignUpButton;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference ref = MainActivity.db.getReference("Owner");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_sign_up);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.EmailInput);
        editTextPassword = findViewById(R.id.InputPassword);
        editTextBrand = findViewById(R.id.BrandInput);
        SignUpButton = findViewById(R.id.SignUpButton);
        progressBar = findViewById(R.id.progressBar);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password,brand;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                brand = editTextBrand.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(StoreOwnerSignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(StoreOwnerSignUpActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(brand)){
                    Toast.makeText(StoreOwnerSignUpActivity.this, "Enter brand name", Toast.LENGTH_SHORT).show();
                    return;
                }
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    //slower than authentication so need to improve
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ownerShot : snapshot.getChildren()) {
                                if (ownerShot.getKey().equals(brand)) {
                                    Toast.makeText(StoreOwnerSignUpActivity.this, "Brand name already under use. Please choose another", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                            createAccount(brand,email,password);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });
            }
        });

    }

    void createAccount(String brand, String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(StoreOwnerSignUpActivity.this, "Account created.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            ref.child(brand).setValue(email);
                            Intent intent = new Intent(StoreOwnerSignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(StoreOwnerSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void fromStoreOwnerToSignUp(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void fromStoreOwnerSignUpToStoreOwnerMain(View view){
        Intent intent = new Intent(this, StoreOwnerMain.class);
        startActivity(intent);
    }

}