package com.dapeng.ui_lib.test;

import android.content.Context;

import com.dapeng.ui_lib.R;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;

import java.util.List;

public class GroupListAdapter extends GroupedRecyclerViewAdapter {

    private List<GroupTestBean> list;

    public GroupListAdapter(Context context, List<GroupTestBean> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> lists = this.list.get(groupPosition).getList();
        return lists == null ? 0 : lists.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.test_head;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.test_footrt;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.test_group_layout;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        holder.setText(R.id.text, "第" + groupPosition + "组，第" + childPosition + "个     " + list.get(groupPosition).getList().get(childPosition));
    }
}
