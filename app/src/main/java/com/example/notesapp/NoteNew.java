package com.example.notesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NoteNew extends AppCompatActivity {

    DatabaseHelper notesDb;
    EditText noteTitle, noteContent;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notesDb = new DatabaseHelper(this);
        notesDb.close();

        noteTitle = (EditText)findViewById(R.id.noteNewTitle);
        noteContent = (EditText)findViewById(R.id.noteNewText);
        btnAddData = (Button)findViewById(R.id.btnAddNewNote);
        addNote();
    }

    public Date getCurrentDate() {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        return date;
    };

    public void addNote() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notesDb.insertNote(noteTitle.getText().toString(), noteContent.getText().toString(), getCurrentDate(), true);
                    }
                }
        );
    }

}
