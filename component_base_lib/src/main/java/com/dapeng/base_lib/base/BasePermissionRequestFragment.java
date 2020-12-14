package com.dapeng.base_lib.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dapeng.base_lib.interfaces.PermissionCallback;
import com.dapeng.base_lib.toast.ToastUtils;

public class BasePermissionRequestFragment extends Fragment {

    //**************** Android M Permission (Android 6.0权限控制代码封装)
    private int permissionRequestCode = 88;
    private PermissionCallback callback;
    protected Context context;
    protected Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }


    public void requestNeedPermissions(@NonNull String applyPermissionInfo, PermissionCallback permissionCallback, String... permission) {
        if (permission == null || permission.length == 0) {
            return;
        }
        this.callback = permissionCallback;

        // 判断系统版本 小于6.0 只需在 Manifest 注册对应权限 大于6.0 还需要 动态申请
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkPermissions(permission)) {
            if (callback != null) {
                callback.hasPermission();
            }
        } else {
            requestPermission(applyPermissionInfo, permissionCallback, permission);
        }


    }

    private void requestPermission(String applyPermissionInfo, PermissionCallback permissionCallback, final String[] permission) {
        if (shouldShowRequestDetailPermissionRationale(permission)) {
            // 用回之前拒绝过一次权限 给个提示
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("权限申请")
                    .setMessage(applyPermissionInfo)
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, permission, permissionRequestCode);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtils.show("权限被拒绝，会导致部分功能异常");
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity, permission, permissionRequestCode);
        }
    }

    private boolean shouldShowRequestDetailPermissionRationale(String[] permission) {
        for (String permissionItem : permission) {
            /*1. 第一次请求权限时,返回 false，用户拒绝了，下一次：shouldShowRequestPermissionRationale()  返回 true，应该显示一些为什么需要这个权限的说明
            2.第二次请求权限时，用户拒绝了，并选择了“不在提醒”的选项时：shouldShowRequestPermissionRationale()  返回 false
            3. 设备的策略禁止当前应用获取这个权限的授权：shouldShowRequestPermissionRationale()  返回 false*/
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查 有权限 有一个没有就会去申请对应的权限
     *
     * @param permission 权限集合
     */
    private boolean checkPermissions(String[] permission) {
        for (String permissionItem : permission) {
            if (ActivityCompat.checkSelfPermission(activity, permissionItem) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != permissionRequestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (verifyPermission(grantResults)) {
            if (callback != null) {
                callback.hasPermission();
            }
        } else {
            if (callback != null) {
                callback.noPermission();
            }
        }


    }

    /**
     * 验证权限
     *
     * @param grantResults 权限回调结果
     */
    private boolean verifyPermission(int[] grantResults) {

        if (grantResults.length == 0) {
            return false;
        }

        for (int permissionState : grantResults) {
            if (permissionState != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
