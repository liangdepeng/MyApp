package com.dapeng.ui_lib.activity;

import androidx.viewpager.widget.ViewPager;

import com.dapeng.ui_lib.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-15
 * <p>
 * Summary:
 */
public class TabManager {

    private final TestUiActivity activity;
    private final TabLayout homeTabLayout;
    private final ViewPager homeViewPager;
    private MusicFragment musicFragment;
    private MoviceFragment moviceFragment;
    private PersonFragment personFragment;
    private VideoFragment videoFragment;

    public TabManager(TestUiActivity activity) {
        this.activity = activity;
        this.homeTabLayout = activity.findViewById(R.id.tab_layout);
        this.homeViewPager = activity.findViewById(R.id.view_pager);

        initFragments();
        initTabLayout();
        initViewPager();
    }

    private void initFragments() {
        musicFragment = new MusicFragment();
        moviceFragment = new MoviceFragment();
        personFragment = new PersonFragment();
        videoFragment = new VideoFragment();



    }

    private void initViewPager() {

    }

    private void initTabLayout() {

    }
}
