package com.dapeng.base_lib.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dapeng.base_lib.R;
import com.dapeng.base_lib.interfaces.IBaseView;
import com.dapeng.utils_lib.DPLogUtils;

public abstract class BaseActivity extends BasePermissionRequestActivity implements IBaseView {

    public String tag = getClass().getSimpleName();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DPLogUtils.errorLevel(tag, tag + "-- onCreate --");
    }

    /**
     * 方法1: Activity.getWindow().getDecorView().findViewById(android.R.id.content)
     * 方法2: Activity.findViewById(android.R.id.content)
     * 方法3: Activity.findViewById(android.R.id.content).getRootView()
     *
     * @return contentView 获取 activity 根view DecorView 相当于根布局
     */
    @Override
    public View getContentView() {
        return (ViewGroup) getWindow().getDecorView();
    }

    /**
     * 判断有没有添加标题栏  xml 文件添加 include< @layout ___自定义通用标题的布局>
     * <p>
     * 判断用里面的一个 viewid 就可以 找不到即没有添加 当然排除 android studio 发病的情况，碰到过添加了也不行 然后找半天原因
     * 最后 clean project -> rebuild project ,还有终极操作 Invalidate caches/Restart 能解决大部分的 缓存乱七八糟的问题 比如：
     * xml 文件写了viewid 但是 找不到id， color文件配置了颜色 找不到 等等资源相关的问题 一般都是 android studio的问题
     * clean project -> rebuild project 终极操作 Invalidate caches/Restart 整个世界都清净了
     */
    private boolean hasTitleLayout() {
        if (getContentView().findViewById(R.id.back_iv) == null) {
            throw new RuntimeException("xml not add include<--base_layout_title-->");
        } else {
            return true;
        }
    }

    /**
     * 需要先在 xml 布局中 添加 标题布局使用  把布局 include 进去
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


    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("正在加载中...");
        }

        // 判断是否是主线程 ，子线程不会显示的，不是主线程 给它 post 丢到主线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    @Override
    public void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
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

    @Override
    public void onBackPressed() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
