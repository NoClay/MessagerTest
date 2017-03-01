package com.example.no_clay.messagertest.Item244;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.no_clay.messagertest.Data.Book;
import com.example.no_clay.messagertest.Data.IBookManager;
import com.example.no_clay.messagertest.Data.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BookManagerService extends Service {
    private static final String TAG = "BookManagerActivity";
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners
            = new RemoteCallbackList<>();
    Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //利用Permission权限验证
            int check = checkCallingOrSelfPermission("com.example.no_clay.messagertest" +
                    ".permission.ACCESS_BOOK_MANAGER_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED){
                return false;
            }
            //利用包名验证
            String packageName = null;
            String[] packages = getPackageManager()
                    .getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0){
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.example.no_clay")){
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            onNewBookArrived(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);
            final int size = mListeners.beginBroadcast();
            Log.d(TAG, "unregisterListener: size = " + size);
            mListeners.finishBroadcast();
        }
    };

    private void onNewBookArrived(Book book) {
        mBooks.add(book);
        final int size = mListeners.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mListeners.getBroadcastItem(i);
            try {
                listener.onNewBookArrivedListener(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListeners.finishBroadcast();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBooks.add(new Book("唯物主义", "num123"));
        mBooks.add(new Book("我也不知道是什么", "num143"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.example.no_clay.messagertest" +
                ".permission.ACCESS_BOOK_MANAGER_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED){
            return null;
        }
        return mBinder;
    }

}
