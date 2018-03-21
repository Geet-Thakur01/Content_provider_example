package com.floatingera.bacancyentry.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by geet on 1/2/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "table_user";
    private static final String DATA_BASE_NAME = "bacancy.db";
    private static final int DATA_BASE_VERSION = 1;

    //Constants for table and columns
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String DOB = "dob";

    public static final String[] ALL_COLUMNS =
            {FIRST_NAME,LAST_NAME,EMAIL,DOB};


    SQLiteDatabase db;
    private final Context context;
    //Constructor...
    public DBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        this.context=context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(_ID INTEGER PRIMARY KEY AUTOINCREMENT,first_name varchar,last_name varchar,email varchar,dob varchar)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
