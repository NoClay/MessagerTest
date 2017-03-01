package com.example.no_clay.messagertest.Data;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by no_clay on 2017/2/26.
 */

public class User implements Serializable, Parcelable{

    private static final long serialVersionUID = -2534337785069283741L;
    String name;
    String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public User() {
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

    protected User(Parcel in) {
        name = in.readString();
        id = in.readString();
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
