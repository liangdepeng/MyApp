package com.dapeng.base_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;

import java.util.List;

/**
 * ListView / GridView 适配器
 *
 * @param <T> itemBean model 数据模型
 * @author liangdepeng
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list;
    private int layoutResId;

//    public BaseListAdapter(Context context) {
//        this(context, null);
//    }
//
//    public BaseListAdapter(Context context, @LayoutRes int layoutResId) {
//        this(context, null, -1);
//    }
//
//    public BaseListAdapter(Context context, List<T> list) {
//        this(context, list, -1);
//    }

    public BaseListAdapter(Context context, List<T> list, @LayoutRes int layoutResId) {
        this.context = context;
        this.list = list;
        this.layoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            if (-1 == layoutResId)
                throw new RuntimeException("A beautiful guy , Not Set ItemLayout yet !!! Please set LayoutRes at First !");

            if (null == context)
                throw new RuntimeException("context is not null! Please check it!");

            convertView = LayoutInflater.from(context).inflate(layoutResId, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        onBindItemData(position, viewHolder, list.get(position));

        return convertView;
    }

    protected abstract void onBindItemData(int position, ViewHolder viewHolder, T itemData);

}
