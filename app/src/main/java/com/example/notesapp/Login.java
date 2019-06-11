package com.example.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.notesapp.Register.md5;
import static com.example.notesapp.DatabaseHelper.*;

public class Login extends AppCompatActivity {

    DatabaseHelper notesDb;
    EditText editUsername, editPassword;
    Button btnLoginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        TextView registerText = findViewById(R.id.go_to_register);
        registerText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openRegisterPage();
            }
        });

        notesDb = new DatabaseHelper(this);
        notesDb.close();

        editUsername = (EditText)findViewById(R.id.login_username);
        editPassword = (EditText)findViewById(R.id.login_password);

        btnLoginUser = (Button)findViewById(R.id.button);
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(editUsername.getText().toString());
            }
        });
    }
    public void openRegisterPage() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openNotesPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loginUser(String editUsername) {
        DatabaseHelper db = new DatabaseHelper(this);
        String hashedPassword = md5(editPassword.getText().toString());
        String password = db.getUserPassword(editUsername);

        if (hashedPassword.equals(password)) {
            openNotesPage();
        } else {
            Toast.makeText(Login.this, "Password is incorrect",Toast.LENGTH_LONG).show();
        }
    }
}
