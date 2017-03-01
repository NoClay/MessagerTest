package com.example.no_clay.messagertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.no_clay.messagertest.BinderPool.BinderPoolActivity;
import com.example.no_clay.messagertest.Data.User;
import com.example.no_clay.messagertest.Item241.BundleActivity;
import com.example.no_clay.messagertest.Item242.FileActivity;
import com.example.no_clay.messagertest.Item243.MessengerActivity;
import com.example.no_clay.messagertest.Item244.BookManagerActivity;
import com.example.no_clay.messagertest.Item245.BookProviderActivity;
import com.example.no_clay.messagertest.Item246.SocketActivity;
import com.example.no_clay.messagertest.Util.MyConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    TextView mButton1;
    @BindView(R.id.button2)
    TextView mButton2;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.button0)
    TextView mButton0;
    @BindView(R.id.button3)
    TextView mButton3;
    @BindView(R.id.button4)
    TextView mButton4;
    @BindView(R.id.button5)
    TextView mButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
    R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                User user = new User("测试玩家", "num123");
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                Intent intent = new Intent(this, BundleActivity.class);
                intent.putExtra("user", bundle);
                startActivity(intent);
                break;
            case R.id.button1:
                Intent intent1 = new Intent(this, MessengerActivity.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                startActivity(new Intent(this, BookManagerActivity.class));
                break;
            case R.id.button3:
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(
                            new FileOutputStream(MyConstants.CACHE_FILE_PATH));
                    out.writeObject(new User("测试玩家", "num123"));
                    Intent intent2 = new Intent(this, FileActivity.class);
                    startActivity(intent2);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.button4:
                Intent intent2 = new Intent(this, BookProviderActivity.class);
                startActivity(intent2);
                break;
            case R.id.button5:
                startActivity(new Intent(this, SocketActivity.class));
                break;
            case R.id.button6:{
                startActivity(new Intent(this, BinderPoolActivity.class));
                break;
            }
        }
    }
}
