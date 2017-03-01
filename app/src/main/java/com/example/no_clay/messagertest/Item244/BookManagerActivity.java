package com.example.no_clay.messagertest.Item244;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.no_clay.messagertest.Data.Book;
import com.example.no_clay.messagertest.Data.IBookManager;
import com.example.no_clay.messagertest.Data.IOnNewBookArrivedListener;
import com.example.no_clay.messagertest.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    IBookManager mBookManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mBookManager.asBinder().linkToDeath(mRecipient, 0);
                mBookManager = bookManager;
                bookManager.registerListener(listener);
                List<Book> list = bookManager.getBookList();
                Log.d(TAG, "onServiceConnected: list type = " + list.getClass().getCanonicalName());
                Log.d(TAG, "onServiceConnected: list = " + list.toString());
                bookManager.addBook(new Book("呵呵呵呵呵", "num1232"));
                list = bookManager.getBookList();
                Log.d(TAG, "onServiceConnected: list type = " + list.getClass().getCanonicalName());
                Log.d(TAG, "onServiceConnected: list = " + list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
        }
    };

    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrivedListener(Book newBook) throws RemoteException {
            Log.d(TAG, "onNewBookArrivedListener: one book new = " + newBook.getName() + newBook.getId());
        }
    };

    @Override
    protected void onDestroy() {
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()){
            try {
                Log.d(TAG, "onDestroy: unregister listener = " + listener);
                mBookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager != null){
                mBookManager.asBinder().unlinkToDeath(mRecipient, 0);
                mBookManager = null;
                Intent intent = new Intent(BookManagerActivity.this
                        , BookManagerService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        }
    };

}
