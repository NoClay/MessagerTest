// IOnNewBookArrivedListener.aidl
package com.example.no_clay.messagertest.Data;
import com.example.no_clay.messagertest.Data.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrivedListener(in Book newBook);
}
