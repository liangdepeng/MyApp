package com.dapeng.utils_lib.viewutil;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * 监听页面 所有的输入 Edittext 不为空之后 可以进行下一步操作
 */
public class MyTextWatcher implements TextWatcher {

    private final View targetView;
    private final ArrayList<TextView> list = new ArrayList<>();
    private final AfterTextChangedCallback callback;

    /**
     * 检测所有edittext 内容非空 设置view是否 Enabled
     *
     * @param targetView Button 或者 需要的View
     */
    public MyTextWatcher(@NonNull View targetView) {
        this(targetView, null);
    }

    /**
     * 检测所有edittext 内容非空 设置view是否 Enabled
     * @param targetView Button 或者 需要的View
     * @param callback 可以返回 遍历的 View 如果需要除了非空 需要增加额外的校验
     */
    public MyTextWatcher(@NonNull View targetView, @Nullable AfterTextChangedCallback callback) {
        this.targetView = targetView;
        this.callback = callback;
    }

    /**
     * 添加需要监听的EditText （EditText 继承于 TextView,方便拓展，无需关心）
     *
     */
    public void addEditTexts(TextView... editTexts) {
        if (editTexts == null || editTexts.length <= 0)
            return;

        list.clear();

        // 为每一个 EditText 增加监听 缓存起来
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].addTextChangedListener(this);
            list.add(editTexts[i]);
        }
    }

    /**
     * 移除所有监听
     */
    public void removeAllViews() {
        if (list.size() == 0) return;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).removeTextChangedListener(this);
        }

        list.clear();
    }

    /**
     * 设置 view enabled
     */
    private void setEnabled(boolean enabled) {
        if (targetView == null) return;

        if (enabled == targetView.isEnabled())
            return;

        targetView.setEnabled(enabled);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (list.size() == 0) return;
        //同时获取焦点的只有一个EditText 通过遍历 查询每一个EditText 是否满足条件
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.isEmpty(list.get(i).getText())) {
                setEnabled(false);
                return;
            }

            if (callback != null) {
                if (!callback.textChanged(list.get(i))) {
                    setEnabled(false);
                    return;
                }
            }

        }

        if (callback != null) {
            callback.isSatisfied(true);
        }

        setEnabled(true);
    }

    public interface AfterTextChangedCallback {

        /**
         * 回调遍历所有的 EditText
         *
         * @param editText 遍历到的 EditText 用于实现自定义的筛选条件
         */
        boolean textChanged(TextView editText);

        /**
         * 是否满足非空 和 自定义的筛选条件
         *
         * @param satisfy
         */
        void isSatisfied(boolean satisfy);
    }
}
