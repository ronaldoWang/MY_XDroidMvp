package cn.droidlover.xdroidmvp.sys.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.sys.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends XFragment {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

}
