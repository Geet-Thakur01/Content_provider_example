package com.floatingera.bacancyentry;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.floatingera.bacancyentry.mydatabase.DBHelper;

/**
 * Created by aplitelinux1 on 1/2/18.
 */

public class UserProvider extends ContentProvider {
        private static final String AUTHORITY = "com.floatingera.bacancyentry";
        private static final String BASE_PATH = "table_user";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

        private static final int USER = 1;
        private static final int USER_ID = 2;

        private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            uriMatcher.addURI(AUTHORITY,BASE_PATH, USER);
            uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",USER_ID);
        }

        private SQLiteDatabase database;

        @Override
        public boolean onCreate() {
            DBHelper helper = new DBHelper(getContext());
            database = helper.getWritableDatabase();
            return true;
        }

        @Nullable
        @Override
        public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
            Cursor cursor;
            switch (uriMatcher.match(uri)) {
                case USER:
                    cursor =  database.query(DBHelper.TABLE_NAME,DBHelper.ALL_COLUMNS,
                            s,null,null,null,null +" ASC");
                    break;
                default:
                    throw new IllegalArgumentException("This is an Unknown URI " + uri);
            }
            cursor.setNotificationUri(getContext().getContentResolver(),uri);

            return cursor;
        }

        @Nullable
        @Override
        public String getType(Uri uri) {

            switch (uriMatcher.match(uri)) {
                case USER:
                    return "vnd.android.cursor.dir/contacts";
                default:
                    throw new IllegalArgumentException("This is an Unknown URI " + uri);
            }
        }

        @Nullable
        @Override
        public Uri insert(Uri uri, ContentValues contentValues) {
            long id = database.insert(DBHelper.TABLE_NAME,null,contentValues);

            if (id > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
            throw new SQLException("Insertion Failed for URI :" + uri);

        }

        @Override
        public int delete(Uri uri, String s, String[] strings) {
            int delCount = 0;
            switch (uriMatcher.match(uri)) {
                case USER:
                    delCount =  database.delete(DBHelper.TABLE_NAME,s,strings);
                    break;
                default:
                    throw new IllegalArgumentException("This is an Unknown URI " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return delCount;
        }

        @Override
        public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
            int updCount = 0;
            switch (uriMatcher.match(uri)) {
                case USER:
                    updCount =  database.update(DBHelper.TABLE_NAME,contentValues,s,strings);
                    break;
                default:
                    throw new IllegalArgumentException("This is an Unknown URI " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return updCount;
        }



}
