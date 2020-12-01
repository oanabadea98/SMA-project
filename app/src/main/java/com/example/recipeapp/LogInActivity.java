package com.example.recipeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.Helpers.FirebaseHelper;
import com.example.recipeapp.Helpers.StorageHelper;
import com.example.recipeapp.Models.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {

    private TextView loginText;
    private EditText emailEt, passwordEt;
    private Button btnLogin, btnSignup;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //logare automata
        /*if (user != null){
            startActivity(new Intent(FirebaseLoginActivity.this, HomeScreen.class));
        }*/
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        loginText = findViewById(R.id.tv_login);
        emailEt = findViewById(R.id.et_login);
        passwordEt = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_first_login);
        btnSignup = findViewById(R.id.btn_second_login);
    }

    public void goToRegister(View view) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
    }
    public void login(){
        startActivity(new Intent(LogInActivity.this, HomeScreen.class));
    }

    public void onLogin(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        //verif. campuri goale
        if (emailEt.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        //preluare val
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        //auth
        loginUser(email, password);
    }

    public void loginUser(final String email, final String password) {
        //autentificare propriu-zisa
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            FirebaseHelper.usersDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserEntity userEntity = snapshot.getValue(UserEntity.class);
                                    StorageHelper.getInstance().setUserEntity(userEntity);
                                    startActivity(new Intent(LogInActivity.this, HomeScreen.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(LogInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );}
}