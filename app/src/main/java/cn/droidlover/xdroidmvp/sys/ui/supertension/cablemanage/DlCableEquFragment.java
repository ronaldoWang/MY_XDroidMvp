package cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.adapter.supertension.cablemanage.DlCableEquAdapter;
import cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage.DlCableEquModel;
import cn.droidlover.xdroidmvp.sys.present.supertension.cablemanage.PDlCableEqu;
import cn.droidlover.xdroidmvp.sys.ui.DevelopCustomerFormViewActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class DlCableEquFragment extends XFragment<PDlCableEqu> {

    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    DlCableEquAdapter adapter;
    Map<String, Object> conditionMap = new HashMap<>();

    @Override
    public void initView(Bundle bundle) {
        initAdapter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData(1);
    }

    public void loadData(final Integer page) {
        getP().loadData(page, conditionMap);
    }

    /**
     * 展示数据
     *
     * @param page 页码
     * @param data 数据
     */
    public void showData(int page, List<DlCableEquModel.DlCableEqu> data) {
        if (page > 1) {
            getAdapter().addData(data);
        } else {
            getAdapter().setData(data);
        }

        if (null != data && !data.isEmpty() && data.size() == 10) {
            contentLayout.getRecyclerView().setPage(page, page + 1);
        } else {
            contentLayout.getRecyclerView().setPage(page, page);
        }

        if (getAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        contentLayout.getRecyclerView().verticalLayoutManager(context);
        contentLayout.getRecyclerView().setAdapter(getAdapter());
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        loadData(1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        loadData(page);
                    }
                });
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.getRecyclerView().useDefLoadMoreView();
    }

    /**
     * 获得adapter
     *
     * @return
     */
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new DlCableEquAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<DlCableEquModel.DlCableEqu, DlCableEquAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, DlCableEquModel.DlCableEqu model, int tag, DlCableEquAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    Integer id = model.getId();
                    Router.newIntent(context).to(DevelopCustomerFormViewActivity.class).putInt("id", id).launch();
                }
            });
        }
        return adapter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dl_cable_equ;
    }

    @Override
    public PDlCableEqu newP() {
        return new PDlCableEqu();
    }
}
