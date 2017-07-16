package cn.droidlover.xdroidmvp.sys.net;

import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.sys.net.supertension.cablemanage.CablemanageService;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    //public static final String API_BASE_URL = "http://gank.io/api/";
    //public static final String API_USER = "http://169.254.145.206:8080/jupai/";
    //public static final String API_USER = "http://jupai168.duapp.com/";
    // public static final String API_USER = "http://130.252.102.226:8090/2014001/";
    public static final String API_USER = "http://192.168.0.104:8080/2014001/";

    private static UserService userService;
    private static DevelopCustomerService developCustomerService;
    private static CablemanageService cablemanageService;

    public static UserService getUserService() {
        if (userService == null) {
            synchronized (Api.class) {
                if (userService == null) {
                    userService = XApi.getInstance().getRetrofit(API_USER, true).create(UserService.class);
                }
            }
        }
        return userService;
    }

    public static DevelopCustomerService getDevelopCustomerService() {
        if (developCustomerService == null) {
            synchronized (Api.class) {
                if (developCustomerService == null) {
                    developCustomerService = XApi.getInstance().getRetrofit(API_USER, true).create(DevelopCustomerService.class);
                }
            }
        }
        return developCustomerService;
    }

    public static CablemanageService getCablemanageService() {
        if (cablemanageService == null) {
            synchronized (Api.class) {
                if (cablemanageService == null) {
                    cablemanageService = XApi.getInstance().getRetrofit(API_USER, true).create(CablemanageService.class);
                }
            }
        }
        return cablemanageService;
    }

}
