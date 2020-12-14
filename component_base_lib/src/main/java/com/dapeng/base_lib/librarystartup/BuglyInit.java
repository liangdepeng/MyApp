package com.dapeng.base_lib.librarystartup;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * startup 库测试 自动初始化 无需显式调用
 */
public class BuglyInit implements Initializer<String> {

    @NonNull
    @Override
    public String create(@NonNull Context context) {
        // bugly bug 反馈 统计
        CrashReport.initCrashReport(context, "9703a551c7", false);
        Log.e("LibraryInit","bugly is inited");
        return "buglyInit";
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        // 需要在本类初始化之前 初始化的类 相当于 本类的 前提条件
        return new ArrayList<>();
    }
}
