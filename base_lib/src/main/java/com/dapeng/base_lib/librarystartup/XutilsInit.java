package com.dapeng.base_lib.librarystartup;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XutilsInit implements Initializer<String> {

    @NonNull
    @Override
    public String create(@NonNull Context context) {
        x.Ext.init(((Application) context));
        Log.e("xutils", "xutils is inited");
        return "xutils";
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {

        return new ArrayList<>();

        // 这样写 会让 这个·list· 里面的类初始化在 本类初始化 之前 适用于有依赖关系的库 初始化顺序
        // 相当于 这个库所依赖的库 提前初始化 然后再初始化这个库
       // return new ArrayList<Class<? extends Initializer<?>>>(Arrays.asList(LibraryInit.class));
    }
}
