package com.example.no_clay.messagertest.Item246;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by no_clay on 2017/3/1.
 */

public class TCPService extends Service {
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{
            "Hello， nice to meet you!",
            "Hi, How are you?",
            "What's your name?",
            "No, thanks"
    };
    private static final String TAG = "TCPService";

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.d(TAG, "run: establish tcp server failed, port:8688");
                e.printStackTrace();
                return;
            }
            Log.d(TAG, "run: service state = " + mIsServiceDestoryed);
            while (!mIsServiceDestoryed){
                try {
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "run: accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //接受客户端消息
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        //向客户端发送消息
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(client.getOutputStream())
                ), true
        );
        while (!mIsServiceDestoryed){
            String str = in.readLine();
            Log.d(TAG, "responseClient: receive = " + str);
            if (str == null){
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            out.println(mDefinedMessages[i]);
            Log.d(TAG, "responseClient: send = " + mDefinedMessages[i]);
        }
        Log.d(TAG, "responseClient: client quit");
        out.close();
        in.close();
        client.close();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();

    }
}
