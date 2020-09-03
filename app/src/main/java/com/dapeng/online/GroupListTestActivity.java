package com.dapeng.online;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dapeng.base_lib.base.BaseActivity;
import com.dapeng.ui_lib.test.GroupListAdapter;
import com.dapeng.ui_lib.test.GroupTestBean;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupListTestActivity extends BaseActivity {

    private ArrayList<GroupTestBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_test);

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new GroupListAdapter(this, list));

        for (int i = 0; i < 20; i++) {
            GroupTestBean testBean = new GroupTestBean();

            testBean.setTitle(i + " test");

            List<String> data = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                data.add("测试分组！！");
            }

            testBean.setList(data);

            list.add(testBean);

        }

        ((GroupedRecyclerViewAdapter) recyclerview.getAdapter()).notifyDataChanged();
    }
}
