package com.example.daniel.assignmentthree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 2017-10-27.
 */

public class DatabaseGroups extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DatabaseGroups";
    public static final String TABLE_NAME = "groups_table";
    public static final String COL_ID_1 = "Id";
    public static final String COL_GROUPNAME_2 = "Groupname";


    // This used for the query (query is create table), SQL-string has to be valid, ID is primary key to identify the table rows
    // autoincrement means it will increment automatically even if you dont provide any data for it, it will be added to table
    private static final String DATABASE_CREATE =
            "create table " + TABLE_NAME +
                    "(" + COL_ID_1 + " integer primary key autoincrement, " +
                    COL_GROUPNAME_2 + " text);";

    // This is the constructor which when called will create your database
    public DatabaseGroups(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseGroups.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Method to insert data
    public boolean addGroupInfo(Group group) {
        SQLiteDatabase db = this.getWritableDatabase(); //will create database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_GROUPNAME_2, group.getGroupName());
        long result = db.insert(TABLE_NAME, null, contentValues); //note that insert returns -1 if row cant be inserted
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null); //will query all the data from the table
        return cursor;
    }
}
