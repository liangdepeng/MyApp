package com.process.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dapeng.base_lib.base.BaseActivity;
import com.dapeng.online.R;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/23
 * <p>
 * Summary:
 */
public class ProcessActivity extends BaseActivity {

    private TextView levelTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_test);
        levelTv = findViewById(R.id.level_tv);
        findViewById(R.id.call_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel://10086"));
                startActivity(intent);
            }
        });


        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", -1);
            if (levelTv!=null){
                levelTv.setText("电量 "+level+"%");
            }
        }
    };
}
