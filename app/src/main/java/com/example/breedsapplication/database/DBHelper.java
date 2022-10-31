package com.example.breedsapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "BreedsDB";

    public static final String FAVORITE_TABLE = "Favorite";
    public static final String FAVORITE_ID = "ID";
    public static final String FAVORITE_BREED = "Breed";
    public static final String FAVORITE_IMAGE = "Image";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format(
                        "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT);",
                        FAVORITE_TABLE,
                        FAVORITE_ID,
                        FAVORITE_BREED,
                        FAVORITE_IMAGE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
