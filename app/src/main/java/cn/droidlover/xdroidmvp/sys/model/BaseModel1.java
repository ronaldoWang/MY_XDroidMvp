package cn.droidlover.xdroidmvp.sys.model;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by wanglei on 2016/12/11.
 */

public class BaseModel1 implements IModel {
    protected boolean error;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
