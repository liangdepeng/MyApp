// IAidlInterface.aidl
package com.example.server;

// Declare any non-default types here with import statements
import com.example.server.IAidlCallback;
import com.example.server.IUserObj;


interface IAidlInterface {

    /**
     *
     * for example  test base data return
     *
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setUserObj(IUserObj obj);

    IUserObj getUserObj();

    void setServerMessage(String message);

    String getServerMessage();

    void setCallback(IAidlCallback callback);

    void removeCallback(IAidlCallback callback);
}