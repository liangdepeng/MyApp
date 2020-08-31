package com.dapeng.base_lib.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder {

    private View rootView;
    private SparseArray<View> views;

    public ViewHolder(View rootView) {
        this.rootView = rootView;
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setClickListener(int viewId,View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    // ... 自行添加
}
