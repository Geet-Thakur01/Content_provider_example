package com.floatingera.bacancylist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private static final String AUTHORITY = "com.floatingera.bacancyentry";
    private static final String BASE_PATH = "table_user";
    public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    ListView resultList;
    String all_user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        resultList = findViewById(R.id.resultList);
        ArrayList<String> userlist = new ArrayList<>();
// "projection" defines the columns that will be returned for each row
        String[] projection = {"first_name", "last_name", "email", "dob"};
        Cursor cursor = getContentResolver().query(URI, projection, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String first = cursor.getString(cursor.getColumnIndex("first_name"));
                    String last = cursor.getString(cursor.getColumnIndex("last_name"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    String dob = cursor.getString(cursor.getColumnIndex("dob"));
                    if (userlist != null) {
                        userlist.add(first + " " + last + "\n" + email + "\n" + dob);
                    }

                    all_user = all_user + first + " " + last + "\n";
                }
            }
        }
        if (userlist != null) {
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userlist);
            resultList.setAdapter(itemsAdapter);
        }
    }

}
