package com.dapeng.online;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dapeng.base_lib.base.BaseApplication;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initMapSDK();
    }




    private void initMapSDK() {
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        // 包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
