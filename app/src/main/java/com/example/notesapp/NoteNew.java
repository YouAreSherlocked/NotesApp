package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.sql.Date;


public class NoteNew extends AppCompatActivity {

    boolean isFav;
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
        EditText title = findViewById(R.id.noteNewTitle);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.action_detail_favourite:
                if (item.isChecked()) {
                    item.setIcon(R.drawable.ic_star_border_white_24dp);
                    item.setTitle("Add as Favourite");
                    item.setChecked(false);
                    isFav = false;
                }
                else {
                    item.setIcon(R.drawable.ic_star_white_24dp);
                    item.setTitle("Remove from Favourites");
                    item.setChecked(true);
                    isFav = true;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                       notesDb.insertNote(noteTitle.getText().toString(), noteContent.getText().toString(), getCurrentDate(), isFav);
                       openMainActivityPage();
                    }
                }
        );
    }

    public void openMainActivityPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
