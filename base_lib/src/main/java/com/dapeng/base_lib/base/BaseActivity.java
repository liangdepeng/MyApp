package com.dapeng.base_lib.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.dapeng.base_lib.R;
import com.dapeng.base_lib.interfaces.IBaseView;
import com.dapeng.utils_lib.DPLogUtils;

public abstract class BaseActivity extends BasePermissionRequestActivity implements IBaseView {

    private String tag = getClass().getSimpleName();
    private Handler handler;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        DPLogUtils.errorLevel(tag, tag + "-- onCreate --");
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("     正在加载中...");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            progressDialog.show();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            });
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("     正在加载中...");
        }
        if (!progressDialog.isShowing())
            return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            progressDialog.dismiss();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DPLogUtils.errorLevel(tag, tag + "-- onStart --");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DPLogUtils.errorLevel(tag, tag + "-- onResume --");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DPLogUtils.errorLevel(tag, tag + "-- onPause --");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DPLogUtils.errorLevel(tag, tag + "-- onStop --");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DPLogUtils.errorLevel(tag, tag + "-- onDestroy --");
    }

    /**
     * 方法1: Activity.getWindow().getDecorView().findViewById(android.R.id.content)
     * 方法2: Activity.findViewById(android.R.id.content)
     * 方法3: Activity.findViewById(android.R.id.content).getRootView()
     *
     * @return contentView
     */
    @Override
    public View getContentView() {
        return (ViewGroup) getWindow().getDecorView();
    }

    private boolean hasTitleLayout() {
        if (getContentView().findViewById(R.id.back_iv) == null) {
            throw new RuntimeException("xml not add include<--base_layout_title-->");
        } else {
            return true;
        }
    }

    /**
     * 需要现在 xml 布局中 添加 标题布局使用
     *
     * @param isShow 是否显示返回按钮
     * @param title  标题内容
     */
    public void showBackIvTitleAndFunction(boolean isShow, CharSequence title) {
        if (hasTitleLayout()) {
            View backIv = getContentView().findViewById(R.id.back_iv);
            if (backIv == null) return;
            backIv.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
            backIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            TextView titleTv = getContentView().findViewById(R.id.title_tv);
            if (titleTv == null) return;
            titleTv.setText(title);
        }
    }
}
