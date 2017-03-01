package com.example.no_clay.messagertest.Item246;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.no_clay.messagertest.R;
import com.example.no_clay.messagertest.Util.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketActivity extends AppCompatActivity {

    @BindView(R.id.input)
    EditText mInput;
    @BindView(R.id.send)
    Button mSend;
    @BindView(R.id.receive)
    TextView mReceive;
    private Socket mClientSocket = null;
    private PrintWriter mPrintWriter = null;
    public static final int MESSAGE_SOCKET_CONNECTED = 0;
    public static final int MESSAGE_RECEIVE_DATA = 1;
    private static final String TAG = "SocketActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);
        initView();
        Intent service = new Intent(this, TCPService.class);
        startService(service);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initSocket();
//                connectTCPServer();
            }
        }).start();
    }

    private void initView() {
        mSend.setEnabled(false);
    }
    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed, retry...");
            }
        }

        try {
            // 接收服务器端的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            while (!SocketActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive :" + msg);
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg = "server " + time + ":" + msg
                            + "\n";
                    handler.obtainMessage(MESSAGE_RECEIVE_DATA, showedMsg)
                            .sendToTarget();
                }
            }
            System.out.println("quit...");
            MyUtils.close(mPrintWriter);
            MyUtils.close(br);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    private void initSocket() {
        Socket socket = null;
        while (socket == null) {

            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "initSocket: connect successful ");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.d(TAG, "initSocket: connect failed, retry...");
            }
        }

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (!SocketActivity.this.isFinishing()){
                String str = in.readLine();
                Log.d(TAG, "initSocket: receive = " + str);
                if (str != null){
                    handler.obtainMessage(MESSAGE_RECEIVE_DATA, str).sendToTarget();
                }
            }
            mPrintWriter.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SOCKET_CONNECTED: {
                    mSend.setEnabled(true);
                    break;
                }
                case MESSAGE_RECEIVE_DATA:{
                    mReceive.setText((String)msg.obj);
                    break;
                }
            }
        }
    };

    @OnClick(R.id.send)
    public void onClick() {
        String msg = mInput.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null){
            mPrintWriter.println(msg);
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onDestroy();
    }
}
