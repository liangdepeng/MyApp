package com.dapeng.online.launcher;

import com.dapeng.base_lib.aaatest.TestAdapterActivity;
import com.dapeng.map_lib.MapTestActivity;
import com.dapeng.online.GroupListTestActivity;
import com.dapeng.online.MainActivity;
import com.dapeng.online.banner.BannerTestActivity;
import com.dapeng.online.contract.ContractMainActivity;
import com.dapeng.online.scan.ScanActivity;
import com.dapeng.online.webtest.WebTestActivity;
import com.process.example.client.ClientActivity;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-18
 * <p>
 * Summary:
 */
public enum ClassEnum {

    // MainActivity
    ACTIVITY_MAIN("描述", MainActivity.class, false),

    ACTIVITY_BANNER_TEST("banner测试", BannerTestActivity.class,false),

    ACTIVITY_CONTRACT_PERSON_TEST("联系人", ContractMainActivity.class,false),

    //ACTIVITY_CUSTOM_SCAN_TEST("自定义扫码", CustomScanActivity.class,false),

    ACTIVITY_DEFAULT_SCAN_TEST("默认扫码", ScanActivity.class,false),

    ACTIVITY_WEB_TEST("网页测试", WebTestActivity.class,false),

    ACTIVITY_GROUP_LIST_ADAPTER_TEST("分组列表测试", GroupListTestActivity.class,false),

    ACTIVITY_BAI_DU_MAP_TEST("百度地图", MapTestActivity.class,false),

    ACTIVITY_TEST_RECYCLERVIEW_ADAPTER_TEST("测试",TestAdapterActivity.class,false),

    ACTIVITY_PROCESS_CONNECT_TEST("进程通信测试", ClientActivity.class,false);

    private String title;
    private Class<?> clz;
    private boolean isDefaultLauncher;

    ClassEnum(String title, Class<?> clz, boolean isDefaultLauncher) {
        this.title = title;
        this.clz = clz;
        this.isDefaultLauncher = isDefaultLauncher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public boolean isDefaultLauncher() {
        return isDefaultLauncher;
    }

    public void setDefaultLauncher(boolean defaultLauncher) {
        isDefaultLauncher = defaultLauncher;
    }
}
