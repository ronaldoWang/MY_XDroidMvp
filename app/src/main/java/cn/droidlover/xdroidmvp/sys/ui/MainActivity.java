package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ToastUtils;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.sys.R;

public class MainActivity extends XActivity {
    AlphaTabsIndicator alphaTabsIndicator;//底部导航
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @Override
    public void initView(Bundle bundle) {
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.addOnPageChangeListener(mainAdapter);
        mViewPager.setOffscreenPageLimit(3);
        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(mViewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        alphaTabsIndicator.getTabView(0).showNumber(6);
//        alphaTabsIndicator.getTabView(1).showNumber(888);
//        alphaTabsIndicator.getTabView(2).showNumber(88);
//        alphaTabsIndicator.getTabView(3).showPoint();
    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(IndexFragment.newInstance());
            fragments.add(SearchFragment.newInstance());
            fragments.add(WorkFragment.newInstance());
            fragments.add(ProjectFragment.newInstance());
            fragments.add(UserFragment.newInstance());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtils.showShortSafe("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }
    //双击手机返回键退出>>>>>>>>>>>>>>>>>>>>>

}
