package com.dapeng.utils_lib;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.IntDef;

import java.io.File;
import java.util.List;


public final class PackageUtil {
    @IntDef(value = {PackageType.TAO_BAO, PackageType.JING_DONG,
            PackageType.PIN_DUO_DUO, PackageType.TIAN_MAO, PackageType.WE_CHAT,
            PackageType.TENCENT_QQ, PackageType.SINA})
    public @interface PackageType{
        int TAO_BAO=0;
        int JING_DONG=1;
        int PIN_DUO_DUO=2;
        int TIAN_MAO=3;
        int WE_CHAT=4;
        int TENCENT_QQ=5;
        int SINA=6;
    }

    public static void installApk(final Context context, final String filePath) {
        installApk(context, new File(filePath));
    }

    public static void installApk(final Context context, final File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  0淘宝 1京东 2拼多多 3天猫 4微信 5QQ 6新浪
     * @param context
     * @param type
     * @return true:已安装；false：未安装
     */
    public static boolean checkAppInstalled(Context context, @PackageType int type) {
        switch (type) {
            case PackageType.TAO_BAO:
                return  isPkgInstalled(context,"com.taobao.taobao");
            case PackageType.JING_DONG:
                return  isPkgInstalled(context,"com.jingdong.app.mall");
            case PackageType.PIN_DUO_DUO:
                return  isPkgInstalled(context,"com.xunmeng.pinduoduo");
            case PackageType.TIAN_MAO:
                return   isPkgInstalled(context,"com.tmall.wireless");
            case PackageType.WE_CHAT:
                return   isPkgInstalled(context,"com.tencent.mm");
            case PackageType.TENCENT_QQ:
                return   isPkgInstalled(context,"com.tencent.mobileqq");
            case PackageType.SINA:
                return   isPkgInstalled(context,"com.sina.weibo")||isPkgInstalled(context,"com.sina.weibolite");
        }
        return false;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *    淘宝  com.taobao.taobao
     *    京东    com.jingdong.app.mall
     *    拼多多    com.xunmeng.pinduoduo
     *    美团    com.sankuai.meituan
     *    苏宁易购  com.suning.mobile.ebuy
     *    咸鱼    com.taobao.idlefish
     *    每日优鲜 cn.missfresh.application
     *    微信 com.tencent.mm
     *    QQ com.tencent.mobileqq
     *    微博 com.sina.weibo
     * @param context context
     * @param pkgName 应用包名
     * @return true:已安装；false：未安装
     */
    public static boolean isPkgInstalled(final Context context,final String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName,  PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }



    public static PackageInfo getPackageInfo(Context context) {
        if (null == context) {
            return null;
        }
        context = context.getApplicationContext();
        final PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 检测是否安装微信
     * @param context
     * @return true 安装了
     */
    public static boolean isWeixinInstalled(Context context) {
        return  isPkgInstalled(context,"com.tencent.mm"); }


    /**
     * @return appName
     */
    public static String getAppName(final Context context, String defaultName) {
        final PackageManager manager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = manager.getApplicationInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String appName = defaultName;
        if (applicationInfo != null) {
            try {
                appName = manager.getApplicationLabel(applicationInfo).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appName;
    }

    private static String processName = null;

    public static String getCurrentProcessName(Context context) {
        if (processName == null) {
            final int pid = android.os.Process.myPid();
            final ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> processes = activityManager
                    .getRunningAppProcesses();
            if (processes != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : processes) {
                    if (appProcess.pid == pid) {
                        processName = appProcess.processName;
                        processName = processName.replaceAll(":", "_");
                        break;
                    }
                }
            }
        }
        return processName;
    }

    public static void openUrl(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void evaluateThisApp(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageInfo(context).packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
