package cn.droidlover.xdroidmvp.sys.net;

import cn.droidlover.xdroidmvp.sys.model.UserModel;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ronaldo on 2017/4/22.
 */

public interface UserService {

    @GET("rest/appuser/doLogin")
    Flowable<UserModel> login(@Query("loginName") String userName,
                              @Query("password") String userPwd);
}
