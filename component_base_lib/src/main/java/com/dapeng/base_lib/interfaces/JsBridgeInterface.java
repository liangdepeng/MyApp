package com.dapeng.base_lib.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.hjq.toast.ToastUtils;
import com.just.agentweb.AgentWeb;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-17
 * <p>
 */

public class JsBridgeInterface {

    private final Context context;
    private final AgentWeb agentWeb;

    public JsBridgeInterface(AgentWeb agentWeb, Context context) {
        this.agentWeb = agentWeb;
        this.context = context;
    }

    @JavascriptInterface
    public void showTips(String tips) {
        ToastUtils.show(tips);
    }

    @JavascriptInterface
    public void jumpPage(String pageSchemeUrl) {
        // todo ...
    }
}
