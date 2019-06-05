package com.example.notesapp;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NoteDetail extends AppCompatActivity {
    private static final String TAG = "NoteDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String titleIn = intent.getStringExtra("TITLE");
        String textIn = intent.getStringExtra("TEXT");

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
    }



    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.action_detail_favourite:
                Log.v(TAG, "Star clicked");
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
}
