package com.example.eslamfaisal.myinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COMMA = " ,";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DEFAULT = " DEFAULT 0";
    private static final String NOT_NULL = " NOT NULL";
    private static final String STRING_TYPE = " STRING";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY = " PRIMARY KEY AUTOINCREMENT";

    public String SQL_CREATE_ENTRIES = "CREATE TABLE " + DataContract.ProductEntry.TABLE_NAME +
            "(" +
            DataContract.ProductEntry._ID + INTEGER_TYPE + PRIMARY + COMMA +
            DataContract.ProductEntry.COLUMN_PRODUCT_NAME + TEXT_TYPE + NOT_NULL + COMMA +
            DataContract.ProductEntry.COLUMN_PRODUCT_PRICE + STRING_TYPE + NOT_NULL + COMMA +
            DataContract.ProductEntry.COLUMN_PRODUCT_QUANTITY + INTEGER_TYPE + DEFAULT + COMMA +
            DataContract.ProductEntry.COLUMN_PRODUCT_IMAGE + TEXT_TYPE +
            ");";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
