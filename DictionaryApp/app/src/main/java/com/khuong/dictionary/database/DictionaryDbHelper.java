package com.khuong.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "khuong_dictionary.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "words";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DEFINITION = "definition";

    public DictionaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_WORD + " TEXT PRIMARY KEY," +
                COLUMN_DEFINITION + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);

        // Insert some sample words
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('verisimilitude', 'The appearance of being true or real')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('apple', 'A fruit')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('application', 'A program or piece of software')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('banana', 'A long curved fruit')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('apply', 'Make a formal request or put to practical use')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
