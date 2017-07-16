package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.ui.common.TabEntity;

public class MainActivity extends XActivity {

    IndexFragment indexFragment;
    SearchFragment searchFragment;
    WorkFragment workFragment;
    ProjectFragment projectFragment;
    UserFragment userFragment;

    private String[] titles = {
            "首页",
            "查询",
            "抢修",
            "项目",
            "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.tab_doc,
            R.drawable.tab_search,
            R.drawable.tab_pic,
            R.drawable.tab_study,
            R.drawable.tab_setting
    };
    private int[] mIconSelectIds = {
            R.drawable.tab_doc_selected,
            R.drawable.tab_search_selected,
            R.drawable.tab_pic_selected,
            R.drawable.tab_study_selected,
            R.drawable.tab_setting_selected
    };

    private int currPos = 0;


    private CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void initView(Bundle bundle) {
        tabLayout = (CommonTabLayout) findViewById(R.id.tablayout);

        for (int i = 0; i < mIconSelectIds.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currPos = position;
                FragmentManager manager = getSupportFragmentManager();
                hideFragment();
                switch (position) {
                    case 0:
                        indexFragment = (IndexFragment) FragmentUtils.findFragment(manager, IndexFragment.class);
                        if (indexFragment == null) {
                            indexFragment = IndexFragment.newInstance();
                            FragmentUtils.addFragment(manager, indexFragment, R.id.main_content);
                        } else {
                            FragmentUtils.showFragment(indexFragment);
                        }
                        break;
                    case 1:
                        searchFragment = (SearchFragment) FragmentUtils.findFragment(manager, SearchFragment.class);
                        if (searchFragment == null) {
                            searchFragment = searchFragment.newInstance();
                            FragmentUtils.addFragment(manager, searchFragment, R.id.main_content);
                        } else {
                            FragmentUtils.showFragment(searchFragment);
                        }
                        break;
                    case 2:
                        workFragment = (WorkFragment) FragmentUtils.findFragment(manager, WorkFragment.class);
                        if (workFragment == null) {
                            workFragment = WorkFragment.newInstance();
                            FragmentUtils.addFragment(manager, workFragment, R.id.main_content);
                        } else {
                            FragmentUtils.showFragment(workFragment);
                        }
                        break;
                    case 3:
                        projectFragment = (ProjectFragment) FragmentUtils.findFragment(manager, ProjectFragment.class);
                        if (projectFragment == null) {
                            projectFragment = ProjectFragment.newInstance();
                            FragmentUtils.addFragment(manager, projectFragment, R.id.main_content);
                        } else {
                            FragmentUtils.showFragment(projectFragment);
                        }
                        break;
                    case 4:
                        userFragment = (UserFragment) FragmentUtils.findFragment(manager, UserFragment.class);
                        if (userFragment == null) {
                            userFragment = UserFragment.newInstance();
                            FragmentUtils.addFragment(manager, userFragment, R.id.main_content);
                        } else {
                            FragmentUtils.showFragment(userFragment);
                        }
                        break;
                }
                // fragmentTransaction.commitAllowingStateLoss();//提交事务
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tabLayout.setCurrentTab(0);
        FragmentManager manager = getSupportFragmentManager();
        hideFragment();
        indexFragment = IndexFragment.newInstance();
        FragmentUtils.addFragment(manager, indexFragment, R.id.main_content);
    }

    private void hideFragment() {
        if (indexFragment != null) {
            FragmentUtils.hideFragment(indexFragment);
        }
        if (searchFragment != null) {
            FragmentUtils.hideFragment(searchFragment);
        }
        if (workFragment != null) {
            FragmentUtils.hideFragment(workFragment);
        }
        if (projectFragment != null) {
            FragmentUtils.hideFragment(projectFragment);
        }
        if (userFragment != null) {
            FragmentUtils.hideFragment(userFragment);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        alphaTabsIndicator.getTabView(0).showNumber(6);
//        alphaTabsIndicator.getTabView(1).showNumber(888);
//        alphaTabsIndicator.getTabView(2).showNumber(88);
//        alphaTabsIndicator.getTabView(3).showPoint();
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

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.setCurrentTab(currPos);
//        if (folderFragment != null) {
//            folderFragment.onMyResume();
//        }
    }
}
