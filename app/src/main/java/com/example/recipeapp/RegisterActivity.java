package com.example.recipeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static com.example.recipeapp.Helpers.FirebaseHelper.usersDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerText;
    private EditText regName, regFirstname, regEmail, regAge, regPassword;
    private Button registerButton;

    FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();
        getIntent();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void initializeViews() {
        registerText = findViewById(R.id.tv_register);
        regName = findViewById(R.id.et_name_register);
        regFirstname = findViewById(R.id.et_firstname_register);
        regEmail = findViewById(R.id.et_email_register);
        regAge = findViewById(R.id.et_age_register);
        regPassword = findViewById(R.id.et_password_register);
        registerButton = findViewById(R.id.btn_register);
    }

    public void onRegister(View view) {
        //preluare input-uri
        final String name = regName.getText().toString();
        final String firstname = regFirstname.getText().toString();
        final String email =regEmail.getText().toString();
        final String age = regAge.getText().toString();
        final String password=regPassword.getText().toString();
        //validare input-uri
        if (regEmail.getText().toString().isEmpty() ||regPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "The password must be at least 6 character long!", Toast.LENGTH_SHORT).show();
            return;
        }
        //inregistrare User
        registerUser(name,firstname,email,age,password);
    }



    public void registerUser(final String name, final String firstname, final String email, final String age, final String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null){
                        return;
                    }
                    UserEntity userEntity = new UserEntity(name,firstname,email,age,password);
                    usersDatabase.child(user.getUid()).setValue(userEntity);
                    startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}