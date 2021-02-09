package com.dapeng.ui_lib.activity;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-15
 * <p>
 * Summary:
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private final HashMap<Integer, Fragment> fragmentHashMap;
    private final List<Fragment> fragmentList;

    public TabFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        fragmentHashMap = new HashMap<>();
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    /**
     * 返回值有三种，
     * POSITION_UNCHANGED  默认值，位置没有改变
     * POSITION_NONE       item已经不存在
     * position            item新的位置
     * 当position发生改变时这个方法应该返回改变后的位置，以便页面刷新。
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
