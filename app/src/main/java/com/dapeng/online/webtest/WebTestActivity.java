package com.dapeng.online.webtest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dapeng.base_lib.activity.WebViewActivity;
import com.dapeng.base_lib.base.BaseActivity;
import com.dapeng.online.R;
import com.hjq.toast.ToastUtils;

public class WebTestActivity extends BaseActivity {

    private EditText webUrlEt;
    private View jumpBtn;
    private WebTestActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        instance = this;
        initView();
    }

    private void initView() {
        showBackIvTitleAndFunction(true,"网页测试");
        webUrlEt = findViewById(R.id.edit_text);
        webUrlEt.setText("http://www.baidu.com/");
        jumpBtn = findViewById(R.id.jump_btn);
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = webUrlEt.getText().toString().trim();
                    if (TextUtils.isEmpty(url)) {
                        ToastUtils.show("url 不能为空");
                        return;
                    }
                    startActivity(new Intent(instance, WebViewActivity.class)
                            .putExtra(WebViewActivity.PARAMS_WEB_URL, url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}