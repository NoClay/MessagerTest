package com.example.no_clay.messagertest;

import com.example.no_clay.messagertest.Data.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by no_clay on 2017/2/26.
 */

public class Test {


    public Test() throws IOException, ClassNotFoundException {
        User user = new User("XXX", "123");
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("cache.txt")
        );
        out.writeObject(user);
        out.close();

        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("cache.txt")
        );
        User user1 = (User) in.readObject();
        in.close();
    }
}
