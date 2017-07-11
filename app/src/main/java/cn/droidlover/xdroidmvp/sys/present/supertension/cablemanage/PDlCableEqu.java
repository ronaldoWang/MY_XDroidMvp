package cn.droidlover.xdroidmvp.sys.present.supertension.cablemanage;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Map;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage.DlCableEquModel;
import cn.droidlover.xdroidmvp.sys.net.Api;
import cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage.DlCableEquActivity;
import cn.droidlover.xdroidmvp.sys.widget.LoadingDialog;

/**
 * Created by haoxi on 2017/7/11.
 */

public class PDlCableEqu extends XPresent<DlCableEquActivity> {
    /**
     * 在线加载数据
     *
     * @param page
     * @param conditionMap 查询条件
     */
    public void loadData(final int page, final Map<String, Object> conditionMap) {
        Api.getCablemanageService().queryDlCableEqu(page, conditionMap)
                .compose(XApi.<DlCableEquModel>getApiTransformer())
                .compose(XApi.<DlCableEquModel>getScheduler())
                .compose(getV().<DlCableEquModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<DlCableEquModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showShortToast(error.getMessage());
                    }

                    @Override
                    public void onNext(DlCableEquModel model) {
                        if (model.isSuccess()) {
                            getV().showData(page, model.getData());
                        } else {
                            ToastUtils.showShortToast(model.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }
}
