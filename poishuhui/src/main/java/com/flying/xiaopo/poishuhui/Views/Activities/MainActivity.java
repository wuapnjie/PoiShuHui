package com.flying.xiaopo.poishuhui.Views.Activities;
/**
 * 主界面，由多个fragment组成
 */

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Views.Fragments.MainFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicListFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicBookListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.ll_container)
    CoordinatorLayout ll_container;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.mViewpager)
    ViewPager mViewPager;

    List<Fragment> pagers;

    MainFragment mainFragment;
    ComicListFragment comicListFragment;
    ComicBookListFragment comicBookListFragment, forthFragment;

    public static final int DEVICE_WIDTH;
    public static final int DEVICE_HEIGHT;

    static {
        DEVICE_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
        DEVICE_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        System.out.println(DEVICE_WIDTH);
        System.out.println(DEVICE_HEIGHT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 一些
     * 初始化的设置
     */
    private void init() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_start);

        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        pagers = new ArrayList<>();
        mainFragment = new MainFragment();
        comicListFragment = new ComicListFragment();
        comicBookListFragment = new ComicBookListFragment();
        forthFragment = new ComicBookListFragment();

        comicBookListFragment.setTaskURL(HtmlUtil.URL_COMIC_LIST);
        forthFragment.setTaskURL(HtmlUtil.URL_SHARINKAN);


        pagers.add(mainFragment);
        pagers.add(comicListFragment);
        pagers.add(comicBookListFragment);
        pagers.add(forthFragment);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public static final int PAGER_NUM = 4;
        int[] titles = new int[]{R.string.tab_1, R.string.tab_2, R.string.tab_3, R.string.tab_4};

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagers.get(position);
        }

        @Override
        public int getCount() {
            return PAGER_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(titles[position]);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

}
