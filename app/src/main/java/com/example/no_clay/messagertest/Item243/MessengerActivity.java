package com.example.no_clay.messagertest.Item243;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.no_clay.messagertest.R;
import com.example.no_clay.messagertest.Util.MyConstants;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerService";
    private Messenger mService;
    Context context = this;
    private Messenger mMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_SERVICE:{
                    Log.d(TAG, "handleMessage: get = " + msg.getData().getString("reply"));
                    break;
                }
                default:super.handleMessage(msg);
            }
        }
    });
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello， this is the client!");
            message.replyTo = mMessenger;
            message.setData(bundle);
            try {
                mService.send(message);
            } catch (RemoteException e) {
                Log.e(TAG, "onServiceConnected: ", e);
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(context, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
