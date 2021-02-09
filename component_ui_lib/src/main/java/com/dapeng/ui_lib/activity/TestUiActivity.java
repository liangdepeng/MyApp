package com.dapeng.ui_lib.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dapeng.base_lib.base.BaseActivity;
import com.dapeng.ui_lib.R;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-15
 * <p>
 * Summary:
 */
public class TestUiActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ui);
        initView();
        initTabManager();
    }

    private void initTabManager() {
        TabManager tabManager = new TabManager(this);
    }

    private void initView() {

    }


}
