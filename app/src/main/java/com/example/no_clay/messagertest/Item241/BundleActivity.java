package com.example.no_clay.messagertest.Item241;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.no_clay.messagertest.Data.User;
import com.example.no_clay.messagertest.R;

public class BundleActivity extends AppCompatActivity {

    private static final String TAG = "BundleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        Bundle data = getIntent().getBundleExtra("user");
        User user = data.getParcelable("user");
        Log.d(TAG, "onCreate: userName = " + user.getName());
        Log.d(TAG, "onCreate: id = " + user.getId());
    }
}
