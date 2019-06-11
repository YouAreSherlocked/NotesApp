package com.example.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {

    DatabaseHelper notesDb;
    EditText editUsername, editPassword;
    Button btnAddData;

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

        notesDb = new DatabaseHelper(this);
        notesDb.close();

        editUsername = (EditText)findViewById(R.id.register_username);
        editPassword = (EditText)findViewById(R.id.register_password);
        btnAddData = (Button)findViewById(R.id.register_submit_button);
        addUser();
    }
    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addUser() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notesDb.registerUser(editUsername.getText().toString(), md5(editPassword.getText().toString()));
                    }
                }
        );
    }
}
