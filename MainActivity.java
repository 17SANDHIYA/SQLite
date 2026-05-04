package com.example.sqliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText editId, editName, editLocation, editDesignation;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editLocation = findViewById(R.id.editLocation);
        editDesignation = findViewById(R.id.editDesignation);
        result = findViewById(R.id.result);

        dbHelper = new DBHelper(this);
    }

    // INSERT
    public void insertData(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", editName.getText().toString());
        values.put("location", editLocation.getText().toString());
        values.put("designation", editDesignation.getText().toString());

        db.insert("employee", null, values);
        result.setText("Inserted");

        db.close();
    }

    // VIEW
    public void viewData(View view) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM employee", null);
        StringBuilder data = new StringBuilder();

        while (cursor.moveToNext()) {
            data.append("ID: ").append(cursor.getInt(0))
                    .append("\nName: ").append(cursor.getString(1))
                    .append("\nLocation: ").append(cursor.getString(2))
                    .append("\nDesignation: ").append(cursor.getString(3))
                    .append("\n\n");
        }

        result.setText(data.toString());

        cursor.close();
        db.close();
    }

    // UPDATE
    public void updateData(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", editName.getText().toString());
        values.put("location", editLocation.getText().toString());
        values.put("designation", editDesignation.getText().toString());

        int rows = db.update("employee", values, "id=?",
                new String[]{editId.getText().toString()});

        result.setText(rows + " Updated");

        db.close();
    }

    // DELETE
    public void deleteData(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rows = db.delete("employee", "id=?",
                new String[]{editId.getText().toString()});

        result.setText(rows + " Deleted");

        db.close();
    }
}
