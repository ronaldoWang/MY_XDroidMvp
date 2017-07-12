package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage.DlCableEquActivity;
import cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage.RightSideslipLay;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.HeaderHolder;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.IconTreeItemHolder;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.ProfileHolder;

/**
 * Created by ronaldo on 2017/6/6.
 */

public class SearchFragment extends XFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AndroidTreeView tView;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout leftMenulayout;

    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout rightMessagelayout;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initEvent();
        initLeftLayout();
        initRightLayout();

        //加载Toolbar
        setHasOptionsMenu(true);
        //StatusBarCompat.translucentStatusBar(getActivity());
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        openRightLayout();
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initRightLayout() {
        //View view = getActivity().getLayoutInflater().inflate(R.layout.xiaoxi_layout, null);
        RightSideslipLay menuHeaderView = new RightSideslipLay(getActivity());
        rightMessagelayout.addView(menuHeaderView);

    }

    private void initLeftLayout() {
        View menuView = getActivity().getLayoutInflater().inflate(R.layout.layout_search_menu, null);
        final ViewGroup containerView = (ViewGroup) menuView.findViewById(R.id.ll_menu);

        final TreeNode root = TreeNode.root();

        TreeNode sbzl = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "设备资料")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode xsjl = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "巡视记录")).setViewHolder(new ProfileHolder(getActivity()));
        //TreeNode clark = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Clark Kent")).setViewHolder(new ProfileHolder(getActivity()));
        //TreeNode barry = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Barry Allen")).setViewHolder(new ProfileHolder(getActivity()));


        TreeNode dlsbzl = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_people, "电缆设备资料")).setViewHolder(new HeaderHolder(getActivity()));
        TreeNode tjsszl = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_place, "土鉴设施资料")).setViewHolder(new HeaderHolder(getActivity()));
        sbzl.addChildren(dlsbzl, tjsszl);


        root.addChildren(sbzl, xsjl);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        containerView.addView(tView.getView());
        leftMenulayout.addView(menuView);
    }

    private void initEvent() {

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

            }

            @Override
            public void onDrawerOpened(View arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerClosed(View arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    //左边菜单开关事件

    public void openLeftLayout() {

        if (mDrawerLayout.isDrawerOpen(leftMenulayout)) {

            mDrawerLayout.closeDrawer(leftMenulayout);

        } else {

            mDrawerLayout.openDrawer(leftMenulayout);
        }

    }

    // 右边菜单开关事件

    public void openRightLayout() {

        if (mDrawerLayout.isDrawerOpen(rightMessagelayout)) {

            mDrawerLayout.closeDrawer(rightMessagelayout);

        } else {

            mDrawerLayout.openDrawer(rightMessagelayout);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_list, menu);
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Router.newIntent(context)        //context表示当前上下文
                    .to(DlCableEquActivity.class)    //to()指定目标context
                    .launch();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
}
