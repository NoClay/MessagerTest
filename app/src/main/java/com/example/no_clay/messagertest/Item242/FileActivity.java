package com.example.no_clay.messagertest.Item242;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.no_clay.messagertest.Data.User;
import com.example.no_clay.messagertest.R;
import com.example.no_clay.messagertest.Util.MyConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileActivity extends AppCompatActivity {
    private static final String TAG = "FileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                if (cacheFile.exists()){
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(cacheFile));
                        try {
                            user = (User) in.readObject();
                            Log.d(TAG, "onCreate: userName = " + user.getName());
                            Log.d(TAG, "onCreate: id = " + user.getId());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
