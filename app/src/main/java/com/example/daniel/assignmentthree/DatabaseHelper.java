package com.example.daniel.assignmentthree;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ansambassamabdulhamid on 20/10/17.
 *
 *      Database storing images
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "imageManager";
    private static final int DATABASE_VERSION = 1;
    private static final String ID_IMAGES = "_id_images";
    private static final String TABLE_IMAGES = "images";
    public static final String IMAGE_COL = "image";

    //Table storing images taken by the user
    public static final String CREATE_IMAGE_TABLE = "CREATE TABLE " +
            TABLE_IMAGES + "(" +
            ID_IMAGES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IMAGE_COL + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(sqLiteDatabase);
    }

    /* Inserting images to the database */
    public void insertImageToMyDb(String myImage) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_COL, myImage);
        sql.insert(TABLE_IMAGES, null, contentValues);
        sql.close();
        Log.d("DatabaseHelper","Picture successfully added to the database!");
    }

    /*
    public void deleteAllImages() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(this.TABLE_IMAGES,null,null);
    }
    */

    public Cursor getImageFromMyDb() {
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM " + TABLE_IMAGES, null);
        return cursor;
    }
}
