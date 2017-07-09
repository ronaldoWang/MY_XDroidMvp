package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.HeaderHolder;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.IconTreeItemHolder;
import cn.droidlover.xdroidmvp.sys.ui.tree.holder.ProfileHolder;

/**
 * Created by ronaldo on 2017/6/6.
 */

public class SearchFragment extends XFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private AndroidTreeView tView;

    private View rootView;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        final TreeNode root = TreeNode.root();
        TreeNode myProfile = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.mipmap.ic_menu_archive, "设备材料")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode socialNetworks = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.mipmap.ic_menu_archive, "电缆设备材料")).setViewHolder(new HeaderHolder(getActivity()));
        TreeNode places = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.mipmap.ic_menu_archive, "土建设施材料")).setViewHolder(new HeaderHolder(getActivity()));
        myProfile.addChildren(socialNetworks, places);
        root.addChildren(myProfile);
        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);

        final ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.tree_menu);
        containerView.addView(tView.getView());


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

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
