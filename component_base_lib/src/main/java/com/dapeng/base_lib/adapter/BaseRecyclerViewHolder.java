package com.dapeng.base_lib.adapter;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用 RecyclerView ViewHolder
 *
 * 链式调用
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public static final int COLOR_DEFAULT = -1;
    public static final int SIZE_DEFAULT = -1;
    private final SparseArray<View> views;
    public int mRealPosition = 0;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置 tetView 文字
     */
    public BaseRecyclerViewHolder setText(int viewId, CharSequence text) {
        return setText(viewId, text, COLOR_DEFAULT, SIZE_DEFAULT);
    }

    /**
     * 设置 tetView 文字 + 颜色
     */
    public BaseRecyclerViewHolder setText(int viewId, CharSequence text, @ColorInt int textColor) {
        return setText(viewId, text, textColor, SIZE_DEFAULT);
    }

    /**
     * 设置 tetView 文字 + 大小
     */
    public BaseRecyclerViewHolder setText(int viewId, CharSequence text, float textSize) {
        return setText(viewId, text, COLOR_DEFAULT, textSize);
    }

    /**
     * 设置 tetView 文字 + 颜色 + 大小
     */
    public BaseRecyclerViewHolder setText(int viewId, CharSequence text, @ColorInt int textColor, float textSize) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
            if (COLOR_DEFAULT != textColor) {
                textView.setTextColor(textColor);
            }
            if (SIZE_DEFAULT != textSize) {
                textView.setTextSize(textSize);
            }
        }
        return this;
    }

    /**
     * view 设置背景
     */
    public BaseRecyclerViewHolder setViewBackground(int viewId, Drawable background) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackground(background);
        }
        return this;
    }

    /**
     * 点击事件
     */
    public BaseRecyclerViewHolder setViewClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null && listener != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 长按事件
     */
    public BaseRecyclerViewHolder setViewLongClickListener(int viewId, View.OnLongClickListener longClickListener) {
        View view = getView(viewId);
        if (view != null && longClickListener != null) {
            view.setOnLongClickListener(longClickListener);
        }
        return this;
    }

    /**
     * 设置图片资源
     */
    public BaseRecyclerViewHolder setImageRes(int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
        return this;
    }

    public BaseRecyclerViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public BaseRecyclerViewHolder setViewGone(int viewId, boolean isGone) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(isGone ? View.GONE : View.VISIBLE);
        }
        return this;
    }

    public BaseRecyclerViewHolder setViewVisible(int viewId, boolean isVisible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
        return this;
    }
}
