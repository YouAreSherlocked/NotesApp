package com.example.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static Boolean showFavourites = false;
    private ArrayList<Note> mNotes = new ArrayList<>();
    RecyclerView.Adapter mAdapter;

    DatabaseHelper notesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NotesApp");

        mAdapter = new RecyclerViewAdapter(this, mNotes);
        RecyclerView rView = findViewById(R.id.notesListR);
        rView.setAdapter(mAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNotePage();
            }
        });

        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        notesDb = new DatabaseHelper(this);
        loadNotes(showFavourites);
        notesDb.close();

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                mNotes.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        touchHelper.attachToRecyclerView(rView);
    }

    private void loadNotes(Boolean justFavourites) {

        Cursor result = justFavourites ? notesDb.getAllFavourites() : notesDb.getAllData();
        if (result.getCount() == 0) {
            showMessage("ERROR", "No Data Found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            mNotes.add(new Note(result.getString(0),result.getString(1), result.getString(2), false));
        }
    }

    public void openNewNotePage() {
        Intent intent = new Intent(this, NoteNew.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.action_home_favourites:
                Log.v(TAG, "Star clicked");
                if (item.isChecked()) {
                    item.setIcon(R.drawable.ic_star_border_white_24dp);
                    item.setChecked(false);
                    item.setTitle("Show Favourites");
                    showFavourites = false;
                }
                else {
                    item.setIcon(R.drawable.ic_star_white_24dp);
                    item.setChecked(true);
                    item.setTitle("Show all");
                    showFavourites = true;
                }
                loadNotes(showFavourites);
                return true;
            case R.id.action_home_search:
                Log.v(TAG, "Search clicked");
                if (item.isChecked()) {
                    item.setChecked(false);
                    item.setTitle("Close Search");
                }
                else {
                    item.setChecked(true);
                    item.setTitle("Open Search");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}