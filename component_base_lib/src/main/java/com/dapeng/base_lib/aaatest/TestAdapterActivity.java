package com.dapeng.base_lib.aaatest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dapeng.base_lib.R;
import com.dapeng.base_lib.base.BaseActivity;
import com.dapeng.base_lib.interfaces.IMulTiType;

import java.util.ArrayList;
import java.util.List;

public class TestAdapterActivity extends BaseActivity implements IMulTiType<TestRvBean> {

    private final List<TestRvBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);
        showBackIvTitleAndFunction(true, "列表测试");
        initData();
        initView();
    }

    private void initData() {

        for (int i=0;i<30;i++){
            TestRvBean testRvBean = new TestRvBean();
            testRvBean.setType(i);
            list.add(testRvBean);
        }
    }

    private void initView() {
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        TestRvAdapter testRvAdapter = new TestRvAdapter(this, list, this);
        recyclerview.setAdapter(testRvAdapter);


        View headView1 = LayoutInflater.from(this).inflate(R.layout.item_multype_test_one, recyclerview, false);
        testRvAdapter.addHeadView(headView1,"head01");
        View headView2 = LayoutInflater.from(this).inflate(R.layout.item_multype_test_two, recyclerview, false);
        testRvAdapter.addHeadView(headView2,"head02");

        View footView1 = LayoutInflater.from(this).inflate(R.layout.item_multype_test_three, recyclerview, false);
        testRvAdapter.addFootView(footView1,"foot01");

    }

    @Override
    public int getLayoutType(TestRvBean itemData, int position) {
        switch (position) {
            case 1:
            case 5:
                return R.layout.item_multype_test_two;
            case 2:
            case 4:
                return R.layout.item_multype_test_three;
            default:
                return R.layout.item_multype_test_one;
        }
    }
}