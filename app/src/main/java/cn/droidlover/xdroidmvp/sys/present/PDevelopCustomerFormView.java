package cn.droidlover.xdroidmvp.sys.present;

import com.blankj.utilcode.util.ToastUtils;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.sys.db.OrmLiteManager;
import cn.droidlover.xdroidmvp.sys.model.DevelopCustomerModel;
import cn.droidlover.xdroidmvp.sys.net.Api;
import cn.droidlover.xdroidmvp.sys.ui.DevelopCustomerFormViewActivity;

/**
 * Created by haoxi on 2017/5/11.
 */

public class PDevelopCustomerFormView extends XPresent<DevelopCustomerFormViewActivity> {
    /**
     * 在线查询单个
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
                        ToastUtils.showShortToast(error.getMessage());
                    }

                    @Override
                    public void onNext(DevelopCustomerModel developCustomerModel) {
                        if (developCustomerModel.isSuccess()) {
                            if (!developCustomerModel.getData().isEmpty()) {
                                getV().showData(developCustomerModel.getData().get(0));
                            }
                        } else {
                            ToastUtils.showShortToast(developCustomerModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 离线查询单个
     *
     * @param id
     */
    public void queryNativeOne(final String id) {
        DevelopCustomerModel.DevelopCustomer data = OrmLiteManager.getInstance(getV())
                .getLiteOrm(getV()).queryById(id, DevelopCustomerModel.DevelopCustomer.class);
        if (null == data) {
            ToastUtils.showShortToast("未查询到数据");
        } else {
            getV().showData(data);
        }
    }
}
