package com.dapeng.utils_lib.viewutil;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyTextWatcher implements TextWatcher {

    private View targetView;
    private ArrayList<TextView> list = new ArrayList<>();
    private AfterTextChangedCallback callback;

    /**
     * 检测所有edittext 内容非空 设置view是否 Enabled
     *
     * @param targetView Button 或者 需要的View
     */
    public MyTextWatcher(@NonNull View targetView) {
        this(targetView, null);
    }

    public MyTextWatcher(@NonNull View targetView, @Nullable AfterTextChangedCallback callback) {
        this.targetView = targetView;
        this.callback = callback;
    }

    public void addEditTexts(TextView... editTexts) {
        if (editTexts == null || editTexts.length <= 0)
            return;

        list.clear();

        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].addTextChangedListener(this);
            list.add(editTexts[i]);
        }
    }

    public void removeAllViews() {
        if (list.size() == 0) return;

        for (int i = 0; i < list.size(); i++) {
            list.get(i).removeTextChangedListener(this);
        }

        list.clear();
    }

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
