package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "locations.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table locations(id INTEGER PRIMARY KEY AUTOINCREMENT, address TEXT, latitude TEXT, longitude TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists locations");
    }

    public boolean insertData(String address, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        long result = db.insert("locations", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean updateData(int row_id, String address, String latitude, String longitude){
        String row = String.valueOf(row_id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        long result = db.update("locations",contentValues, "id=?", new String[]{row});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteData(int row_id, String address, String latitude, String longitude){
        String row = String.valueOf(row_id);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("locations", "id=?", new String[]{row});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM locations";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

}
