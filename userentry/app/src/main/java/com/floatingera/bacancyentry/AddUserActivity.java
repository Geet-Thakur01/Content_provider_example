package com.floatingera.bacancyentry;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.floatingera.bacancyentry.mydatabase.DBHandler;
import com.floatingera.bacancyentry.mydatabase.DBHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    EditText first_name, last_name, email, dob;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        //initialize data base
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHandler = new DBHandler(getApplicationContext(), sqLiteDatabase);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);

        Button add = findViewById(R.id.add);
        Button show = findViewById(R.id.show);
        add.setOnClickListener(this);
        show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (isvalidFields()) {
                    addNewUser();
                }else {
                    Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.show:
                openApp(this, "com.floatingera.bacancylist");
                break;
        }
    }

    private boolean isvalidFields() {
        if (first_name.getText().toString().length() > 0 &&
                last_name.getText().toString().length() > 0 &&
                isvaldemail(email.getText().toString()) && dob.getText().toString().length() > 0)
            return true;
        return false;
    }

    private boolean isvaldemail(String s) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = s;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

    }

    private void openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new ActivityNotFoundException();
            } else {
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);
            }

        } catch (Exception e) {
            Toast.makeText(context, "App not found !", Toast.LENGTH_LONG).show();
        }
    }

    private void addNewUser() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", first_name.getText().toString());
        contentValues.put("last_name", last_name.getText().toString());
        contentValues.put("email", email.getText().toString());
        contentValues.put("dob", dob.getText().toString());
        boolean isInsert = dbHandler.insertData(DBHelper.TABLE_NAME, contentValues);
        if (isInsert) {
            Toast.makeText(this, "1 Record Added Successfully", Toast.LENGTH_LONG).show();
            first_name.setText("");
            last_name.setText("");
            email.setText("");
            dob.setText("");
        }
    }
}
