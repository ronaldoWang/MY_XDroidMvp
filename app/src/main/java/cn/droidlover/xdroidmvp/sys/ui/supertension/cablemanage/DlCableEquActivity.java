package cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.adapter.supertension.cablemanage.DlCableEquAdapter;
import cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage.DlCableEquModel;
import cn.droidlover.xdroidmvp.sys.present.supertension.cablemanage.PDlCableEqu;
import cn.droidlover.xdroidmvp.sys.ui.DevelopCustomerFormViewActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

import static cn.droidlover.xdroidmvp.sys.R.id.drawer_layout;

public class DlCableEquActivity extends XActivity<PDlCableEqu> {

    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(drawer_layout)
    DrawerLayout drawer;
    DlCableEquAdapter adapter;
    Map<String, Object> conditionMap = new HashMap<>();

    @Override
    public void initView(Bundle bundle) {
        initAdapter();
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        drawer.openDrawer(GravityCompat.END);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                                Gravity.END);    //解除锁定
                        break;
                }
                return true;
            }
        });
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Router.pop(context);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
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
