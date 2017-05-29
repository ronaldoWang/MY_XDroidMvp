package cn.droidlover.xdroidmvp.sys.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by ronaldo on 2017/4/22.
 */

public class UserModel extends BaseModel<UserModel.User> implements IModel {

    @Table("T_USER")
    public static class User {
        @PrimaryKey(AssignType.BY_MYSELF)
        private String id;
        @NotNull
        @Column("login_name")
        private String loginName;
        @NotNull
        @Column("pwd")
        private String pwd;
        @Column("create_date")
        private String createDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
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
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
