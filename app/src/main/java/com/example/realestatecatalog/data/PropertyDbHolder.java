package com.example.realestatecatalog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PropertyDbHolder extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Property.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String KEY_ID = "_id";
    public static final String APP_PREFERENCES_photo = "photo";
    public static final String APP_PREFERENCES_address = "address";
    public static final String APP_PREFERENCES_area = "area";
    public static final String APP_PREFERENCES_price = "price";
    public static final String APP_PREFERENCES_quantity_room = "quantity_room";
    public static final String APP_PREFERENCES_floor = "floor";
    public static final String APP_PREFERENCES_price_sqm = "price_sqm";


    public PropertyDbHolder(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PROPERTY_TABLE = "create table " + TABLE_CONTACTS + "(" +
                KEY_ID + " integer primary key autoincrement," +
                APP_PREFERENCES_photo + " blob, " +
                APP_PREFERENCES_address + " text, " +
                APP_PREFERENCES_area + " numeric, " +
                APP_PREFERENCES_price + " numeric, " +
                APP_PREFERENCES_quantity_room + " integer, " +
                APP_PREFERENCES_floor + " integer, " +
                APP_PREFERENCES_price_sqm + " numeric " +
                ")";
        db.execSQL(SQL_CREATE_PROPERTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);
    }
}
