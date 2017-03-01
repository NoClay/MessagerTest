package com.example.no_clay.messagertest.Item245;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by no_clay on 2017/2/28.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    public static final int DB_VERSION = 1;
    public static final String CREATE_BOOK_TABLE = "create table if not exists " +
            BOOK_TABLE_NAME + " (id text primary key, name text)";
    public static final String CREATE_USER_TABLE = "create table if not exists " +
            USER_TABLE_NAME + " (id text primary key, name text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
