package com.example.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        TextView registerText = findViewById(R.id.go_to_login);
        registerText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openLoginPage();
            }
        });
    }
    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
