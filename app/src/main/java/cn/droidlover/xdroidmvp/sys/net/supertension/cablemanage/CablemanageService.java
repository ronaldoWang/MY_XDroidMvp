package cn.droidlover.xdroidmvp.sys.net.supertension.cablemanage;

import java.util.Map;

import cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage.DlCableEquModel;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by haoxi on 2017/7/11.
 */

public interface CablemanageService {

    @GET("rest/cablemanage/dlCableEqu/queryList")
    Flowable<DlCableEquModel> queryDlCableEqu(@Query("pageNo") int pageNum, @QueryMap(encoded = true) Map<String, Object> conditionMap);

    //@GET("rest/cablemanage/dlCableEqu/queryOne")
    //Flowable<DlCableEquModel> queryDlCableEquOne(@Query("id") Integer id);
}
