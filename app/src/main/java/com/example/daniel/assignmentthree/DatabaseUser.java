package com.example.daniel.assignmentthree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 2017-10-25.
 */

public class DatabaseUser extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DatabaseUser";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_ID_1 = "Id";
    public static final String COL_USERNAME_2 = "Username";
    public static final String COL_PASSWORD_3 = "Password";


    // This used for the query (query is create table), SQL-string has to be valid, ID is primary key to identify the table rows
    // autoincrement means it will increment automatically even if you dont provide any data for it, it will be added to table
    private static final String DATABASE_CREATE =
            "create table " + TABLE_NAME +
                    "(" + COL_ID_1 + " integer primary key autoincrement, " +
                    COL_USERNAME_2 + " text, " +
                    COL_PASSWORD_3 + " text);";

    // This is the constructor which when called will create your database
    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseUser.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Method to insert data
    public boolean addUserInfo(User user) {
        SQLiteDatabase db = this.getWritableDatabase(); //will create database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME_2, user.getUserName());
        contentValues.put(COL_PASSWORD_3, user.getPassword());
        long result = db.insert(TABLE_NAME, null, contentValues); //note that insert returns -1 if row cant be inserted
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null); //will query all the data from the table , * means "all"
        return cursor;
    }
}
