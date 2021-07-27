package com.example.myapplication3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication3.test.IconListHorizontalView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import kotlin.collections.CollectionsKt;

public class MainActivity extends AppCompatActivity implements IconListHorizontalView.IViewBindCallback {

    //    private RecyclerView recyclerView;
    private IconListHorizontalView iconListV;
    private IconListHorizontalView iconListV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        recyclerView = findViewById(R.id.test_rv);
        iconListV = findViewById(R.id.icon_rv);
        iconListV2 = findViewById(R.id.icon_rv2);
        initView();
    }

    private void initView() {
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.addItemDecoration(new TestItemDecoration(30, 3, 0));
//        recyclerView.setAdapter(new TestAdapter(this));

        IconListHorizontalView.Config config = new IconListHorizontalView.Config();
        config.setOpenPageScrolled(true);
        // 3x5 一页15个
        config.setMaxLines(3);
        config.setSpanCount(5);
        ArrayList<String> list = CollectionsKt.arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        iconListV.setDataConfig(config, this, list);

        IconListHorizontalView.Config config2 = new IconListHorizontalView.Config();
        config2.setItemLayoutResId(R.layout.item_child_icon__layout2);
        config2.setOpenPageScrolled(true);
        // 2x4 一页8个
        config2.setMaxLines(2);
        config2.setSpanCount(4);
       // ArrayList<String> list2 = CollectionsKt.arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        iconListV2.setDataConfig(config2, this, list);
    }

    @Override
    public void bindItemView(@NotNull IconListHorizontalView.IconItemViewHolder holder, @Nullable Object item) {
        // TODO: 2021/7/27
        TextView textView = holder.getView(R.id.classify_title_tv);
        textView.setText(String.valueOf(item));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, String.valueOf(item) + " clicked ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}