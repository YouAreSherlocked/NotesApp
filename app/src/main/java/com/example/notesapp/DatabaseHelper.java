package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notesapp.db";

    public static final String USER_TABLE_NAME = "users_table";
    public static final String U_COL_1 = "ID";
    public static final String U_COL_2 = "USERNAME";
    public static final String U_COL_3 = "PASSWORD";

    public static final String UN_TABLE_NAME = "users_notes_table";
    public static final String UN_COL_1 = "USERID";
    public static final String UN_COL_2 = "NOTESID";

    public static final String NOTES_TABLE_NAME = "notes_table";
    public static final String N_COL_1 = "ID";
    public static final String N_COL_2 = "TITLE";
    public static final String N_COL_3 = "CONTENT";
    public static final String N_COL_4 = "CREATED_DATE";
    public static final String N_COL_5 = "FAV";

    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,USERNAME TEXT UNIQUE,PASSWORD TEXT)");
        db.execSQL("create table " + UN_TABLE_NAME + "(USERID INTEGER NOT NULL REFERENCES USER_TABLE_NAME (ID), NOTESID INTEGER NOT NULL REFERENCES NOTES_TABLE_NAME (ID))");
        db.execSQL("create table " + NOTES_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,TITLE TEXT, CONTENT TEXT,CREATED_DATE DATE DEFAULT CURRENT_DATE,FAV BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER_TABLE_NAME");
        db.execSQL("DROP TABLE IF EXISTS UN_TABLE_NAME");
        db.execSQL("DROP TABLE IF EXISTS NOTES_TABLE_NAME");
        onCreate(db);
    }

    public boolean registerUser(String username, String password) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(U_COL_2, username);
        contentValues.put(U_COL_3, password);
        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertNote(String title, String content, Date created_date, Boolean fav) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(N_COL_2, title);
        contentValues.put(N_COL_3, content);
        contentValues.put(N_COL_4, created_date.toString());
        contentValues.put(N_COL_5, fav);
        long result = db.insert(NOTES_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+NOTES_TABLE_NAME, null);
        return result;
    }

    public Boolean updateNote(Integer id, String title, String content) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(N_COL_1, id);
        contentValues.put(N_COL_2, title);
        contentValues.put(N_COL_3, content);
        db.update(NOTES_TABLE_NAME, contentValues, "ID = ?",new String[] { id.toString() });
        return true;
    }

    public Integer deleteNoteById(Integer id) {
        db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME, "ID = ?", new String[] { id.toString() });
    }
    public Cursor getAllFavourites() {
        db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + NOTES_TABLE_NAME + " where " + N_COL_5 + " = ?" , new String[] { "true" });
        return result;
    }

}
