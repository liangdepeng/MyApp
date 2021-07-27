package com.process.example.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dapeng.base_lib.toast.ToastWrapper;
import com.dapeng.online.IMyAidlCallback;
import com.dapeng.online.IMyAidlInterface;

import java.util.Random;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/23
 * <p>
 * Summary: 远程服务 运行在独立进程中 跨进程通信
 */
public class MyAidlService extends Service {

    public final String tag = MyAidlService.class.getSimpleName();

    private final Handler handler=new Handler(Looper.getMainLooper());

    public MyAidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(tag, "MyAidlService - onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        showMsg(this, "MyAidlService - onBind");

        Log.e(tag, "MyAidlService - onBind");

        return new MyBinder(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {

        showMsg(this, "MyAidlService - onUnbind");

        Log.e(tag, "MyAidlService - onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {

        showMsg(this, "MyAidlService - 销毁 ");

        Log.e(tag, "MyAidlService - onDestroy");
        super.onDestroy();
    }

    /**
     * {@link RemoteCallbackList}
     *
     * 负责维护远程接口列表的繁重工作，通常用于执行从 {@link android.app.Service} 到其客户端的回调。
     * 特别是： <ul> <li> 跟踪一组已注册的 {@link #IInterface} 回调，注意通过其底层唯一的 {@link IBinder}
     * （通过调用 {@link #IInterfaceasBinder IInterface.asBinder( )}.
     * <li> 为每个注册的接口附加一个{@link IBinder.DeathRecipient IBinder.DeathRecipient}，
     * 这样如果它的进程消失了，它就可以从列表中清除。
     */
    private final RemoteCallbackList<IMyAidlCallback> mCallbacks = new RemoteCallbackList<IMyAidlCallback>();

    private void callback(String msg) {
        // 准备开始调用当前注册的回调。这将创建回调列表的副本，
        // 您可以使用 {@link getBroadcastItem} 从中检索项目。
        // 请注意，一次只能激活一个广播，因此您必须确保始终从同一线程调用它
        // （通常通过使用 {@link Handler} 进行调度）或进行自己的同步。
        // 完成后您必须调用 {@link finishBroadcast}
        int count = mCallbacks.beginBroadcast();
        for (int i = 0; i < count; i++) {
            try {
                mCallbacks.getBroadcastItem(i).testCallback(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        private final String tag = MyBinder.class.getSimpleName();
        private MyAidlService myAidlService;

        public MyBinder(MyAidlService service) {
            this.myAidlService = service;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setServerMessage(String message) throws RemoteException {

            myAidlService.handler.post(new Runnable() {
                @Override
                public void run() {
                    showMsg(myAidlService, "MyBinder - setServerMessage - 收到客户端消息" + message);
                }
            });


            Log.e(tag, "MyBinder - setServerMessage - " + message);

            myAidlService.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myAidlService.callback("来自服务端的消息回调，服务端确认收到消息");
                }
            },3000);
        }

        @Override
        public String getServerMessage() throws RemoteException {

            Log.e(tag, "MyBinder - getServerMessage - ");

            myAidlService.handler.post(new Runnable() {
                @Override
                public void run() {
                    showMsg(myAidlService, "MyBinder server Message 来自服务端的消息 ");
                }
            });

            return "MyBinder server Message 来自服务端的消息 " + (new Random().nextInt(1000));
        }

        @Override
        public void setCallback(IMyAidlCallback callback) throws RemoteException {
            if (callback != null) {
                myAidlService.mCallbacks.register(callback);
            }
        }

        @Override
        public void removeCallback(IMyAidlCallback callback) throws RemoteException {
            if (callback != null) {
                myAidlService.mCallbacks.unregister(callback);
            }
        }

    }

    public static void showMsg(Context context, String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            ToastWrapper.show(context, text, Toast.LENGTH_SHORT, Gravity.CENTER);
        }

    }
}
