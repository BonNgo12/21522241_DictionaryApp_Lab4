package com.khuong.dictionary.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.khuong.dictionary.database.DictionaryDbHelper;

import com.khuong.dictionary.R;

public class MainActivity extends AppCompatActivity {
    private DictionaryDbHelper dbHelper;
    private EditText editTextWord;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DictionaryDbHelper(this);
        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonLookup = findViewById(R.id.buttonLookup);

        buttonLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookupWord();
            }
        });
    }

    private void lookupWord() {
        String userInput = editTextWord.getText().toString().trim();
        if (userInput.isEmpty()) {
            textViewResult.setText("Please enter a word");
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DictionaryDbHelper.COLUMN_DEFINITION +
                " FROM " + DictionaryDbHelper.TABLE_NAME +
                " WHERE " + DictionaryDbHelper.COLUMN_WORD + " = ?", new String[]{userInput});

        if (cursor.moveToFirst()) {
            String definition = cursor.getString(0);
            textViewResult.setText("Definition: " + definition);
        } else {
            // If not found, search for substrings
            cursor = db.rawQuery("SELECT " + DictionaryDbHelper.COLUMN_WORD +
                    " FROM " + DictionaryDbHelper.TABLE_NAME +
                    " WHERE " + DictionaryDbHelper.COLUMN_WORD + " LIKE ?", new String[]{"%" + userInput + "%"});

            if (cursor.moveToFirst()) {
                StringBuilder matches = new StringBuilder("Did you mean:\n");
                do {
                    matches.append("- ").append(cursor.getString(0)).append("\n");
                } while (cursor.moveToNext());
                textViewResult.setText(matches.toString());
            } else {
                textViewResult.setText("No matches found.");
            }
        }
        cursor.close();
        db.close();
    }
}