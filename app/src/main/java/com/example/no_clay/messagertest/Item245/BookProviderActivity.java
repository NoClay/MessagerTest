package com.example.no_clay.messagertest.Item245;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.no_clay.messagertest.Data.Book;
import com.example.no_clay.messagertest.Data.User;
import com.example.no_clay.messagertest.R;

public class BookProviderActivity extends AppCompatActivity {
    private static final String TAG = "BookProviderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_provider);
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
//        ContentValues values = new ContentValues();
//        values.put("id", "num196");
//        values.put("name", "程序设计的艺术");
//        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.setId(bookCursor.getString(0));
            book.setName(bookCursor.getString(1));
            Log.d(TAG, "onCreate: book name = " + book.getName());
        }
        bookCursor.close();

        Uri userUri = BookProvider.USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"id", "name"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.setId(userCursor.getString(0));
            user.setName(userCursor.getString(1));
            Log.d(TAG, "query user:" + user.toString());
        }
        userCursor.close();
    }
}
