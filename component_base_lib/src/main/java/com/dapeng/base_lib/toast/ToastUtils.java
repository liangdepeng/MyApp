package com.dapeng.base_lib.toast;

import android.widget.Toast;

import com.dapeng.utils_lib.ContextHolder;

public class ToastUtils {

    public static void show(CharSequence text) {
        ToastWrapper.show(ContextHolder.getInstance().getAppContext(), text, Toast.LENGTH_SHORT);
    }
}
