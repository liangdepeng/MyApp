package com.dapeng.base_lib.base;

import android.app.Application;
import android.content.Context;

import com.dapeng.utils_lib.ContextHolder;
import com.dapeng.utils_lib.DPLogUtils;

public class BaseApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        DPLogUtils.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appContext = base;
        ContextHolder.getInstance().init(base);
    }

    public static Context getAppContext() {
        return appContext;
    }


}
