package com.example.no_clay.messagertest.Util;

import android.os.Environment;

/**
 * Created by no_clay on 2017/2/22.
 */

public class MyConstants {
    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath()
            + "/cache/";
    public static final String CACHE_FILE_PATH = CACHE_PATH + "cacheData";
}
