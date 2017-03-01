package com.example.no_clay.messagertest.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by no_clay on 2017/2/27.
 */

public class Book implements Parcelable{

    String name;
    String id;

    public Book(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }

    protected Book(Parcel in) {
        name = in.readString();
        id = in.readString();
    }



    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
