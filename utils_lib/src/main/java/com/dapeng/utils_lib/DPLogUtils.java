package com.dapeng.utils_lib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * 日调试志打印 工具类 release 版本 会关闭 开启日志 隐响性能
 */
public class DPLogUtils {

    public static boolean isDebug;

    /**
     * 判断当前应用是否是debug状态
     *
     *  //  isDebug=!"release".equals(BuildConfig.BUILD_TYPE);
     */
    public static void init(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            isDebug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            isDebug = false;
        }
    }

    public static void verboseLevel(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void verboseLevel(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.v(tag, msg, tr);
        }
    }

    public static void debugLevel(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void debugLevel(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void infoLevel(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void infoLevel(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    public static void warnLevel(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void warnLevel(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    public static void errorLevel(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void errorLevel(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

}
