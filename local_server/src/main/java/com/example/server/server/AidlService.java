package com.example.server.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.server.IAidlCallback;
import com.example.server.IAidlInterface;
import com.example.server.IUserObj;

import java.util.Random;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/24
 * <p>
 * Summary:
 */
public class AidlService extends Service {

    private final Handler handler = new Handler(Looper.getMainLooper());

    public AidlService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showMsg(this,"远程服务绑定成功",0);
        return new MyBinder(this);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        showMsg(this,"远程服务解绑成功",0);
        super.unbindService(conn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    private final RemoteCallbackList<IAidlCallback> mCallbacks = new RemoteCallbackList<>();

    private void callback(String msg) {

        int count = mCallbacks.beginBroadcast();
        for (int i = 0; i < count; i++) {
            try {
                mCallbacks.getBroadcastItem(i).testCallback(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }

    static class MyBinder extends IAidlInterface.Stub {

        private AidlService context;

        public MyBinder(AidlService context) {
            this.context = context;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setUserObj(IUserObj obj) throws RemoteException {

        }

        @Override
        public IUserObj getUserObj() throws RemoteException {
            return null;
        }

        @Override
        public void setServerMessage(String message) throws RemoteException {
            context.showMsg(context,"服务端收到客户端的消息 ："+message,0);

            context.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.callback("来自服务端的回执消息");
                }
            },3000);
        }

        @Override
        public String getServerMessage() throws RemoteException {
            return "服务端的消息————"+(new Random().nextInt(1000));
        }

        @Override
        public void setCallback(IAidlCallback callback) throws RemoteException {
            if (callback!=null){
                context.mCallbacks.register(callback);
            }
        }

        @Override
        public void removeCallback(IAidlCallback callback) throws RemoteException {
            if (callback!=null){
                context.mCallbacks.unregister(callback);
            }
        }

    }

    public void showMsg(Context context,String msg,long delayMills){
        if (Looper.myLooper()==null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            },delayMills);
        }else {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
