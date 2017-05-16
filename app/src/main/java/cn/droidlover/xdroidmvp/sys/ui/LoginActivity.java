package cn.droidlover.xdroidmvp.sys.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.model.UserModel;
import cn.droidlover.xdroidmvp.sys.model.common.Constent;
import cn.droidlover.xdroidmvp.sys.present.PUser;
import cn.droidlover.xdroidmvp.sys.widget.LoadingDialog;

public class LoginActivity extends XActivity<PUser> {
    @BindView(R.id.login_edit_name)
    EditText et_userName;
    @BindView(R.id.login_edit_pwd)
    EditText et_userPwd;
    @BindView(R.id.login_cb_savepwd)
    CheckBox cb_save;
    @BindView(R.id.login_btn_login_online)
    TextView btn_login_online;
    @BindView(R.id.login_btn_login_unline)
    TextView btn_login_unline;
    SharedPref sharedPref;
    String userName = "";
    String userPwd = "";

    @Override
    public void initView(Bundle bundle) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        sharedPref = SharedPref.getInstance(context);
        userName = sharedPref.getString("userName", "");
        userPwd = sharedPref.getString("userPwd", "");
        boolean isChecked = sharedPref.getBoolean("isChecked", false);
        et_userName.setText(userName);
        et_userPwd.setText(userPwd);
        cb_save.setChecked(isChecked);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public PUser newP() {
        return new PUser();
    }

    /**
     * 登录
     */
    @OnClick({R.id.login_btn_login_online, R.id.login_btn_login_unline})
    public void click(View v) {
        userName = et_userName.getText().toString();
        userPwd = et_userPwd.getText().toString();
        if (StringUtils.isTrimEmpty(userName)) {
            ToastUtils.showLongToast("用户名不能为空");
            return;
        }
        if (StringUtils.isTrimEmpty(userPwd)) {
            ToastUtils.showLongToast("密码不能为空");
            return;
        }
        LoadingDialog.showDialogForLoading(context);
        switch (v.getId()) {
            case R.id.login_btn_login_online:
                Constent.ONLINE = true;
                getP().login(userName, userPwd);//在线登录
                break;
            case R.id.login_btn_login_unline:
                Constent.ONLINE = false;
                getP().unLineLogin(userName, userPwd);//离线登录
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     *
     * @param user
     */
    public void doLogin(UserModel.User user) {
        if (cb_save.isChecked()) {
            sharedPref.putString("userName", userName);
            sharedPref.putString("userPwd", userPwd);
            sharedPref.putBoolean("isChecked", true);
        } else {
            sharedPref.putString("userName", "");
            sharedPref.putString("userPwd", "");
            sharedPref.putBoolean("isChecked", false);
        }
        sharedPref.put("curUser", user);//设置当前登陆人
//        Router.newIntent(context)
//                .to(MainActivity.class)    //to()指定目标context
//                .launch();
        Router.newIntent(context)
                .to(DevelopCustomerActivity.class)    //to()指定目标context
                .launch();
        context.finish();
    }
}
