package com.example.notesapp;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notesapp.dal.NotesDao;
import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mTexts = new ArrayList<>();
    private ArrayList<Note> mNotes = new ArrayList<>();

    DatabaseHelper notesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NotesApp");
        //loadNotesToHome();
        initNotes();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNotePage();
            }
        });

        notesDb = new DatabaseHelper(this);
        notesDb.close();
    }

    private void initNotes() {
        mTitles.add("Title 111");
        mTexts.add("Text 111");
        mTitles.add("Title 222");
        mTexts.add("Text 222");
        mTitles.add("Title 222");
        mTexts.add("Text 222");
        mTitles.add("Title 222");
        mTexts.add("Text 222");
        mTitles.add("Title 222");
        mTexts.add("Text 222");
        mTitles.add("Title 222");
        mTexts.add("Text 222");
        mTitles.add("Title 222");
        mTexts.add("Text 222");

        mNotes.add(new Note("Note 1", "Das ist die erste Notiz. Juhuu!", true));
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rView = findViewById(R.id.notesListR);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mTitles, mTexts);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openNewNotePage() {
        Intent intent = new Intent(this, NoteNew.class);
        startActivity(intent);
    }

    public void loadNotesToHome() {
        ListView notes = findViewById(R.id.notesList);
        ArrayAdapter<Note> noteAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        noteAdapter.addAll(NotesDao.getAll());
        notes.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
