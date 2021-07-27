// IMyAidlInterface.aidl
package com.dapeng.online;

// Declare any non-default types here with import statements
import com.dapeng.online.IMyAidlCallback;


interface IMyAidlInterface {

    /**
     *
     * for example  test base data return
     *
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setServerMessage(String message);

    String getServerMessage();

    void setCallback(IMyAidlCallback callback);

    void removeCallback(IMyAidlCallback callback);
}