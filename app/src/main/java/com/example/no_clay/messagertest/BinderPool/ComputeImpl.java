package com.example.no_clay.messagertest.BinderPool;

import android.os.RemoteException;

/**
 * Created by no_clay on 2017/3/1.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
