package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.sys.adapter.GanhuoAdapter;
import cn.droidlover.xdroidmvp.sys.model.GankResults;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/31.
 */

public class GanhuoFragment extends BasePagerFragment {

    GanhuoAdapter adapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new GanhuoAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, GanhuoAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, GanhuoAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (tag) {
                        case GanhuoAdapter.TAG_VIEW:
                            WebActivity.launch(context, model.getUrl(), model.getDesc());
                            break;
                    }
                }
            });
        }
        return adapter;
    }


    @Override
    public void initView(Bundle bundle) {

    }

    @Override
    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    @Override
    public String getType() {
        return "Android";
    }

    public static GanhuoFragment newInstance() {
        return new GanhuoFragment();
    }
}
