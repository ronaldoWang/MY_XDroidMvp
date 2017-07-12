package cn.droidlover.xdroidmvp.sys.present;

import com.blankj.utilcode.util.ToastUtils;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.model.DevelopCustomerModel;
import cn.droidlover.xdroidmvp.sys.net.Api;
import cn.droidlover.xdroidmvp.sys.ui.DevelopCustomerFormEditActivity;
import cn.droidlover.xdroidmvp.sys.widget.LoadingDialog;

/**
 * Created by haoxi on 2017/4/25.
 */

public class PDevelopCustomerForm extends XPresent<DevelopCustomerFormEditActivity> {

    /**
     * 查询单个
     *
     * @param id
     */
    public void queryOne(final String id) {
        Api.getDevelopCustomerService().queryOne(id)
                .compose(XApi.<DevelopCustomerModel>getApiTransformer())
                .compose(XApi.<DevelopCustomerModel>getScheduler())
                .compose(getV().<DevelopCustomerModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<DevelopCustomerModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtils.showShortSafe(error.getMessage());
                    }

                    @Override
                    public void onNext(DevelopCustomerModel developCustomerModel) {
                        if (developCustomerModel.isSuccess()) {
                            if (!developCustomerModel.getData().isEmpty()) {
                                getV().showData(developCustomerModel.getData().get(0));
                            }
                        } else {
                            ToastUtils.showShortSafe(developCustomerModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 保存对象
     *
     * @param data
     */
    public void save(DevelopCustomerModel.DevelopCustomer data) {
        LoadingDialog.showDialogForLoading(getV());//加载框
        Api.getDevelopCustomerService().save(data.getDataMap())
                .compose(XApi.<DevelopCustomerModel>getApiTransformer())
                .compose(XApi.<DevelopCustomerModel>getScheduler())
                .compose(getV().<DevelopCustomerModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<DevelopCustomerModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showShortSafe("保存失败");
                    }

                    @Override
                    public void onNext(DevelopCustomerModel developCustomerModel) {
                        if (developCustomerModel.isSuccess()) {
                            ToastUtils.showShortSafe(developCustomerModel.getMessage());
                            Router.pop(getV());
                        } else {
                            ToastUtils.showShortSafe(developCustomerModel.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }
}
