package com.dapeng.base_lib.adapter;

import android.content.Context;

import java.util.List;

class MyAdapter extends BaseListAdapter<String>{


    public MyAdapter(Context context, List<String> list, int layoutResId) {
        super(context, list, layoutResId);
    }


    @Override
    protected void onBindItemData(int position, ViewHolder viewHolder, String itemData) {

    }
}
