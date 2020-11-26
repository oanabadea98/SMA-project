package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView first_tv,second_tv;
    private Button main_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setOnClickListeners();
    }

    public void initializeViews(){
        first_tv = findViewById(R.id.tv_first_text);
        second_tv = findViewById(R.id.tv_second_text);
        main_bt = findViewById(R.id.btn_main_button);
    }
    private void setOnClickListeners()
    {

        main_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                toLoginActivity();
            }


        });
    }

    public void toLoginActivity()
    {
        Intent intent = new Intent(this , LogInActivity.class);
        startActivity(intent);

    }
}