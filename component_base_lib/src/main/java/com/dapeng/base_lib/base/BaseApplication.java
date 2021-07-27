package com.dapeng.base_lib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dapeng.utils_lib.common.ContextHolder;
import com.dapeng.utils_lib.log.DPLogUtils;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        DPLogUtils.init(this);
        registerActivityLifecycleCallbacks(this);
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


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityCreated -- Activity : "+activity.toString());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityStarted -- Activity : "+activity.toString());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityResumed -- Activity : "+activity.toString());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityPaused -- Activity : "+activity.toString());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityStopped -- Activity : "+activity.toString());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        DPLogUtils.errorLevel("activity_lifecycle_监控","Application -- onActivityDestroyed -- Activity : "+activity.toString());
    }
}
