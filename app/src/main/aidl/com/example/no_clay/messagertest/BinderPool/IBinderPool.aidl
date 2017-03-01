// IBinderPool.aidl
package com.example.no_clay.messagertest.BinderPool;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
