package com.dapeng.base_lib.toast;

import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import com.dapeng.utils_lib.common.ContextHolder;

public class ToastUtil {

    public static void show(CharSequence text) {
        show(text, Gravity.NO_GRAVITY);
    }

    public static void show(CharSequence text, int gravity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast toast = Toast.makeText(ContextHolder.getInstance().getAppContext(), text, Toast.LENGTH_SHORT);
            toast.setGravity(gravity, 0, 0);
            toast.show();
        } else {
            ToastWrapper.show(ContextHolder.getInstance().getAppContext(), text, Toast.LENGTH_SHORT, gravity);
        }

    }
}
