package com.example.no_clay.messagertest.Util;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by no_clay on 2017/2/26.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public MyApplication() {
        Log.d(TAG, "MyApplication: initDirs");
        initDirs();
    }

    private void initDirs(){
        File file = new File(MyConstants.CACHE_PATH);
        if (!file.exists() || !file.isDirectory()){
            file.mkdir();
            Log.d(TAG, "MyApplication: initDirs success");
        }
        File data = new File(MyConstants.CACHE_FILE_PATH);
        if (!data.exists() || !data.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
