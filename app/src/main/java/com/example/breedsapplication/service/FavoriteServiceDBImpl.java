package com.example.breedsapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.breedsapplication.database.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteServiceDBImpl implements FavoriteService {
    private final DBHelper dbHelper;

    public FavoriteServiceDBImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public FavoriteServiceDBImpl(Context context) {
        this(new DBHelper(context));
    }

    @Override
    public Map<String, List<String>> list() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.FAVORITE_TABLE,
                new String[]{DBHelper.FAVORITE_BREED, DBHelper.FAVORITE_IMAGE},
                null, null, null, null, null);

        int breedIndex = cursor.getColumnIndex(DBHelper.FAVORITE_BREED);
        int imageIndex = cursor.getColumnIndex(DBHelper.FAVORITE_IMAGE);

        Map<String, List<String>> data = new HashMap<>();
        while (cursor.moveToNext()) {
            String breed = cursor.getString(breedIndex);
            String image = cursor.getString(imageIndex);

            if (!data.containsKey(breed)) {
                data.put(breed, new ArrayList<>());
            }
            data.get(breed).add(image);
        }

        cursor.close();
        return data;
    }

    @Override
    public void insert(String breed, String image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FAVORITE_BREED, breed);
        cv.put(DBHelper.FAVORITE_IMAGE, image);

        db.insert(DBHelper.FAVORITE_TABLE, null, cv);
    }

    @Override
    public void remove(String image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.FAVORITE_TABLE,
                String.format("%s = ?", DBHelper.FAVORITE_IMAGE),
                new String[]{image});
    }

    @Override
    public void removeBreed(String breed) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.FAVORITE_TABLE,
                String.format("%s = ?", DBHelper.FAVORITE_BREED),
                new String[]{breed});
    }

    @Override
    public boolean isFavorite(String image) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.FAVORITE_TABLE,
                new String[]{DBHelper.FAVORITE_BREED, DBHelper.FAVORITE_IMAGE},
                String.format("%s = ?", DBHelper.FAVORITE_IMAGE),
                new String[]{image},
                null,
                null,
                null);

        boolean result = cursor.getCount() > 0;
        cursor.close();

        return result;
    }
}
