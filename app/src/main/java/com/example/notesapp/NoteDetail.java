package com.example.notesapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.model.Note;

import static java.lang.Integer.parseInt;

public class NoteDetail extends AppCompatActivity {
    private static final String TAG = "NoteDetail";

    DatabaseHelper notesDb;
    EditText noteTitle, noteContent;
    Button btnUpdateNote;
    private static String id;
    private static boolean isFav;
    private Menu menu;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        isFav = getIntent().getExtras().getBoolean("FAV");
        String titleIn = intent.getStringExtra("TITLE");
        String textIn = intent.getStringExtra("TEXT");
        userId = intent.getIntExtra("USERID", 0);

        SharedPreferences sharedpreferences = getSharedPreferences("SHARED_USERID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("USERID", userId);
        editor.commit();

        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(titleIn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText content2 = findViewById(R.id.noteDetailText);
        content2.setText(textIn);

        EditText title = findViewById(R.id.noteDetailTitle);
        title.setText(titleIn);
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

        notesDb = new DatabaseHelper(this, userId);

        noteTitle = (EditText)findViewById(R.id.noteDetailTitle);
        noteContent = (EditText)findViewById(R.id.noteDetailText);
        btnUpdateNote = (Button)findViewById(R.id.update_button);
        updateNoteData();
    }


    public void updateNoteData() {
        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), isFav);
                boolean isUpdated = notesDb.updateNote(id, note);
                if (isUpdated == true) {
                    Toast.makeText(NoteDetail.this, "Note successfully saved",Toast.LENGTH_LONG).show();
                    openMainPage();
                }
                else {
                    Toast.makeText(NoteDetail.this, "ERROR: Note has not been saved",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem star = menu.findItem(R.id.action_detail_favourite);
        star.setIcon(!isFav ? R.drawable.ic_star_white_24dp : R.drawable.ic_star_border_white_24dp);
        star.setChecked(!isFav ? true : false);
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
                }
                else {
                    item.setIcon(R.drawable.ic_star_white_24dp);
                    item.setTitle("Remove from Favourites");
                    item.setChecked(true);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("USERID", userId);
        startActivity(intent);
    }
}
