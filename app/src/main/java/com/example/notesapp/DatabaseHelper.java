package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Date;

import static java.lang.Integer.parseInt;

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

    private int userId;

    SQLiteDatabase db;

    public DatabaseHelper(Context context, int userId) {
        super(context, DATABASE_NAME, null, 1);
        this.userId = userId;
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

    public boolean insertNote(String title, String content, Date created_date, boolean fav) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(N_COL_2, title);
        contentValues.put(N_COL_3, content);
        contentValues.put(N_COL_4, created_date.toString());
        contentValues.put(N_COL_5, fav ? 1 : 0);
        long result = db.insert(NOTES_TABLE_NAME, null, contentValues);
        if (result == -1 || !insertUserNote(userId, result)) { //TO DO Make dynamic
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertUserNote(int userId, long noteId) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UN_COL_1, userId);
        contentValues.put(UN_COL_2, noteId);
        long result = db.insert(UN_TABLE_NAME, null, contentValues);
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

    public Boolean updateNote(Integer id, String title, String content, boolean fav) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(N_COL_1, id);
        contentValues.put(N_COL_2, title);
        contentValues.put(N_COL_3, content);
        contentValues.put(N_COL_5, fav ? 1 : 0);
        db.update(NOTES_TABLE_NAME, contentValues, "ID = ?",new String[] { id.toString() });
        return true;
    }

    public Integer deleteNoteById(Integer id) {
        db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME, "ID = ?", new String[] { id.toString() });
    }

    public String getUserPassword(String username) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE_NAME, new String[] { U_COL_3 }, "USERNAME = ?", new String[] { username }, null, null, null, null);

        if (cursor.getCount() == 0) {
            return "";
        } else {
            cursor.moveToFirst();
            String password = cursor.getString(0);
            return password;
        }
    }
    public Cursor getAllFavourites() {
        db = this.getWritableDatabase();
        return db.rawQuery("select * from " + NOTES_TABLE_NAME + " where " + N_COL_5 + " = ?" , new String[] { "1" });
    }

    public int getUserIdByName(String editUsername) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from " + USER_TABLE_NAME + " where " + U_COL_2 + " = ?" , new String[] { editUsername });
        cursor.moveToFirst();
        return parseInt(cursor.getString(0));
    }
}
