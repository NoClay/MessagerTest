package com.example.no_clay.messagertest.Item245;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.no_clay.messagertest.Util.MyConstants;

/**
 * Created by no_clay on 2017/2/28.
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = MyConstants.PACKAGE_NAME + ".BookProvider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    public Context mContext;
    private SQLiteDatabase mDatabase;


    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (URI_MATCHER.match(uri)){
            case 0:tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case 1:tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: thread = " + Thread.currentThread().getName());
        mContext = getContext();
        mDatabase = new DBOpenHelper(mContext,
                DBOpenHelper.DB_NAME,
                null,
                DBOpenHelper.DB_VERSION).getWritableDatabase();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                initData();
//            }
//        }).start();
        return false;
    }

//    private void initData() {
//        mDatabase.execSQL("insert into book values('num012', 'Android')");
//        ContentValues value = new ContentValues();
//        value.put("id", "num014");
//        value.put("name", "Java编程思想");
//        mDatabase.insert(DBOpenHelper.BOOK_TABLE_NAME, null, value);
//        value.clear();
//        value.put("id", "num016");
//        value.put("name", "Android开发艺术探索");
//        mDatabase.insert(DBOpenHelper.BOOK_TABLE_NAME, null, value);
//        value.clear();
//        value.put("id", "04141087");
//        value.put("name", "高XX");
//        mDatabase.insert(DBOpenHelper.USER_TABLE_NAME, null, value);
//        value.clear();
//        value.put("id", "04141085");
//        value.put("name", "莫X");
//        mDatabase.insert(DBOpenHelper.USER_TABLE_NAME, null, value);
//        value.clear();
//        value.put("id", "04141086");
//        value.put("name", "张XX");
//        mDatabase.insert(DBOpenHelper.USER_TABLE_NAME, null, value);
//
//    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: thread = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("不支持的URI");
        }
        return mDatabase.query(table, projection, selection, selectionArgs,
                null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: thread = " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: thread = " + Thread.currentThread().getName());
        Log.d(TAG, "insert: uri = " + uri);
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("不支持的URI");
        }
        mDatabase.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: thread = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("不支持的URI");
        }
        int count = mDatabase.delete(table, selection, selectionArgs);
        if (count > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: thread = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("不支持的URI");
        }
        int row = mDatabase.update(table, values, selection, selectionArgs);
        if (row > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }
}
