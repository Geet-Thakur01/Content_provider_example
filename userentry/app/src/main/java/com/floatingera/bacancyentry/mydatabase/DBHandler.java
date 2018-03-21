package com.floatingera.bacancyentry.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by geet on 1/2/18.
 */

public class DBHandler {

    SQLiteDatabase db;
    @SuppressWarnings("unused")
    private Context context;

    public DBHandler(Context context, SQLiteDatabase db) {
        this.context = context;
        this.db = db;
    }

    public boolean insertData(String tableName, ContentValues values) {
        long i = db.insert(tableName, null, values);
        Log.e("insert success",i+"");
        return i > 0 ? true : false;
    }

    public boolean deleteData(String tableName, String whereColumnEqual, String whereColumnValue[]) {
        SQLiteDatabase mydb = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        int i = mydb.delete(tableName, whereColumnEqual, whereColumnValue);
        mydb.close();
        return i > 0 ? true : false;
    }

    public boolean updateData(String tableName, ContentValues values, String selection, String selectionArgs[]) {
        SQLiteDatabase mydb = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        int i = mydb.update(tableName, values, selection, selectionArgs);
        mydb.close();
        return i > 0 ? true : false;
    }

    public Cursor getData(String query, String selectionArgs[]) {
        SQLiteDatabase mydb = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mydb.rawQuery(query, selectionArgs);
    }
}
