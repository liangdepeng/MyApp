package com.example.server.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.server.IAidlInterface;
import com.example.server.R;
import com.example.server.server.AidlService;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/24
 * <p>
 * Summary: 启动远程服务
 */
public class LauncherServerActivity extends AppCompatActivity {

    private Button bindBtn;
    private boolean bind = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bind = true;
            iAidlInterface = IAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iAidlInterface = null;
            bind = false;
        }
    };

    private IAidlInterface iAidlInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_server_test);
        bindBtn = findViewById(R.id.bind_tv);
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bind) {
                    unbindService(connection);
                    iAidlInterface = null;
                    bind = false;
                    bindBtn.setText("绑定服务");
                } else {
                    Intent intent = new Intent(LauncherServerActivity.this, AidlService.class);
                    boolean bindService = bindService(intent, connection, BIND_AUTO_CREATE);
                    bindBtn.setText(bindService+ "解除绑定");
                }
            }
        });

    }
}
