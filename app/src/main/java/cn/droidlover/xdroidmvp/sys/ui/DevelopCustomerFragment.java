package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.adapter.DevelopCustomerFragmentAdapter;
import cn.droidlover.xdroidmvp.sys.model.DevelopCustomerModel;
import cn.droidlover.xdroidmvp.sys.model.common.Constent;
import cn.droidlover.xdroidmvp.sys.present.PDevelopCustomer;
import cn.droidlover.xdroidmvp.sys.widget.XCSlideView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by haoxi on 2017/4/25.
 */

public class DevelopCustomerFragment extends XFragment<PDevelopCustomer> {
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    DevelopCustomerFragmentAdapter adapter;

    XCSlideView mSlideViewRight;//搜索侧滑框
    int mScreenWidth = 0;//屏幕宽度

    String search = "";//查询json

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
                    ((SimpleDialog.Builder) builder).message("是否删除?")
                            .positiveAction("确认")
                            .negativeAction("取消");
                    DialogFragment fragment = DialogFragment.newInstance(builder);
                    fragment.show(getFragmentManager(), null);
                }
            });

        }
        return adapter;
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        setLayoutManager(contentLayout.getRecyclerView());
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
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
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

    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        loadData(1);
    }

    @Override
    public void initView(Bundle bundle) {
        mScreenWidth = ScreenUtils.getScreenWidth();
        View menuViewLeft = LayoutInflater.from(context).inflate(R.layout.layout_slideview, null);
        mSlideViewRight = XCSlideView.create(this.getActivity(), XCSlideView.Positon.LEFT);
        mSlideViewRight.setMenuView(this.getActivity(), menuViewLeft);
        mSlideViewRight.setMenuWidth(mScreenWidth * 7 / 9);
    }

    public void loadData(final Integer page) {
        if (Constent.ONLINE) {
            getP().loadData(page, search);
        } else {
            getP().loadNativeData(page);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

    @Override
    public PDevelopCustomer newP() {
        return new PDevelopCustomer();
    }

    public static DevelopCustomerFragment newInstance() {
        return new DevelopCustomerFragment();
    }
}
