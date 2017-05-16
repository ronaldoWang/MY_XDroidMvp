package cn.droidlover.xdroidmvp.sys.present;

import com.blankj.utilcode.util.ToastUtils;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.sys.db.OrmLiteManager;
import cn.droidlover.xdroidmvp.sys.model.DevelopCustomerModel;
import cn.droidlover.xdroidmvp.sys.model.common.Constent;
import cn.droidlover.xdroidmvp.sys.net.Api;
import cn.droidlover.xdroidmvp.sys.ui.DevelopCustomerActivity;
import cn.droidlover.xdroidmvp.sys.widget.LoadingDialog;

/**
 * Created by haoxi on 2017/4/25.
 */

public class PDevelopCustomer1 extends XPresent<DevelopCustomerActivity> {
    public void loadData(final int page, final String search) {
        Api.getDevelopCustomerService().query(page, search)
                .compose(XApi.<DevelopCustomerModel>getApiTransformer())
                .compose(XApi.<DevelopCustomerModel>getScheduler())
                .compose(getV().<DevelopCustomerModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<DevelopCustomerModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showShortToast(error.getMessage());
                    }

                    @Override
                    public void onNext(DevelopCustomerModel developCustomerModel) {
                        LoadingDialog.cancelDialogForLoading();
                        if (developCustomerModel.isSuccess()) {
                            getV().showData(page, developCustomerModel.getData());
                        } else {
                            ToastUtils.showShortToast(developCustomerModel.getMessage());
                        }
                    }
                });
    }

    public void delete(final String customerNo) {
        LoadingDialog.showDialogForLoading(getV());
        Api.getDevelopCustomerService().delete(customerNo)
                .compose(XApi.<DevelopCustomerModel>getApiTransformer())
                .compose(XApi.<DevelopCustomerModel>getScheduler())
                .compose(getV().<DevelopCustomerModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<DevelopCustomerModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showShortToast(error.getMessage());
                    }

                    @Override
                    public void onNext(DevelopCustomerModel developCustomerModel) {
                        LoadingDialog.cancelDialogForLoading();
                        if (developCustomerModel.isSuccess()) {
                            getV().loadData(1);
                        } else {
                            ToastUtils.showShortToast(developCustomerModel.getMessage());
                        }
                    }
                });
    }

    public void loadNativeData(final int page, final String search) {
        List<DevelopCustomerModel.DevelopCustomer> data = OrmLiteManager.getInstance(getV())
                .getLiteOrm(getV())
                .query(new QueryBuilder<DevelopCustomerModel.DevelopCustomer>(DevelopCustomerModel.DevelopCustomer.class)
                        .limit((page - 1) * Constent.PAGE_SIZE, Constent.PAGE_SIZE));
        getV().showData(page, data);
    }
}
