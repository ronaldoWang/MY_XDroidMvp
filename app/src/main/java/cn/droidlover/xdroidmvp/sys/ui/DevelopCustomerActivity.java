package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ScreenUtils;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.adapter.DevelopCustomerFragmentAdapter;
import cn.droidlover.xdroidmvp.sys.model.DevelopCustomerModel;
import cn.droidlover.xdroidmvp.sys.model.common.Constent;
import cn.droidlover.xdroidmvp.sys.present.PDevelopCustomer1;
import cn.droidlover.xdroidmvp.sys.widget.XCSlideView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class DevelopCustomerActivity extends XActivity<PDevelopCustomer1> {

    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    DevelopCustomerFragmentAdapter adapter;
    XCSlideView mSlideViewLeft;//搜索侧滑框

    String search = "";//查询json

    @Override
    public void initView(Bundle bundle) {
        int mScreenWidth = ScreenUtils.getScreenWidth();
        //加载侧滑界面
        View menuViewLeft = LayoutInflater.from(context).inflate(R.layout.layout_slideview, null);
        mSlideViewLeft = XCSlideView.create(this, XCSlideView.Positon.LEFT);
        mSlideViewLeft.setMenuView(this, menuViewLeft);
        mSlideViewLeft.setMenuWidth(mScreenWidth * 7 / 9);
        EditText iv = ButterKnife.findById(menuViewLeft, R.id.edit_customer_customerName);

        //加载Toolbar
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        if (!mSlideViewLeft.isShow())
                            mSlideViewLeft.show();
                        else
                            mSlideViewLeft.dismiss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        loadData(1);
    }

    /**
     * 获得adapter
     *
     * @return
     */
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new DevelopCustomerFragmentAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<DevelopCustomerModel.DevelopCustomer, DevelopCustomerFragmentAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, DevelopCustomerModel.DevelopCustomer model, int tag, DevelopCustomerFragmentAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    String id = model.getId();
                    Router.newIntent(context).to(DevelopCustomerFormViewActivity.class).putString("id", id).launch();
                }

                @Override
                public void onItemLongClick(int position, final DevelopCustomerModel.DevelopCustomer model, int tag, DevelopCustomerFragmentAdapter.ViewHolder holder) {
                    super.onItemLongClick(position, model, tag, holder);
                    Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                        @Override
                        public void onPositiveActionClicked(DialogFragment fragment) {
                            super.onPositiveActionClicked(fragment);
                            getP().delete(model.getCustomerNo());
                        }

                        @Override
                        public void onNegativeActionClicked(DialogFragment fragment) {
                            super.onNegativeActionClicked(fragment);
                        }
                    };
                    ((SimpleDialog.Builder) builder).message(getResources().getString(R.string.comfirm_delete))
                            .positiveAction(getResources().getString(R.string.ok))
                            .negativeAction(getResources().getString(R.string.cancel));
                    DialogFragment fragment = DialogFragment.newInstance(builder);
                    fragment.show(getSupportFragmentManager(), null);
                }
            });

        }
        return adapter;
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
     * 展示数据
     *
     * @param page 页码
     * @param data 数据
     */
    public void showData(int page, List<DevelopCustomerModel.DevelopCustomer> data) {
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

    public void loadData(final Integer page) {
        if (Constent.ONLINE) {
            getP().loadData(page, search);
        } else {
            getP().loadNativeData(page, search);
        }
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

    @OnClick({R.id.btn_search, R.id.btn_reset})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                doSearch();
                break;
            case R.id.btn_reset:
                doReset();
                break;
        }
    }

    /**
     * 重置
     */
    private void doReset() {
    }

    /**
     * 查询
     */
    private void doSearch() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_developcustomer;
    }

    @Override
    public PDevelopCustomer1 newP() {
        return new PDevelopCustomer1();
    }
}
