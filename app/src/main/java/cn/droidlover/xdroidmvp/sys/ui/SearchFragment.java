package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private AndroidTreeView tView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        final TreeNode root = TreeNode.root();

        TreeNode myProfile = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "My Profile")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode bruce = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Bruce Wayne")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode clark = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Clark Kent")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode barry = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, "Barry Allen")).setViewHolder(new ProfileHolder(getActivity()));

        addProfileData(myProfile);
        addProfileData(clark);
        addProfileData(bruce);
        addProfileData(barry);


        root.addChildren(myProfile, bruce, barry, clark);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        return rootView;
    }

    private void addProfileData(TreeNode profile) {
        TreeNode socialNetworks = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_people, "Social")).setViewHolder(new HeaderHolder(getActivity()));
        TreeNode places = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_place, "Places")).setViewHolder(new HeaderHolder(getActivity()));

        profile.addChildren(socialNetworks, places);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;

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
