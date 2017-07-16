package cn.droidlover.xdroidmvp.sys.event.supertension.cablemanage;

import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.event.IBus;

/**
 * Created by ronaldo on 2017/7/15.
 */

public class CablemanageEvent implements IBus.IEvent {


    public static final String dlCableEquFragment = "DlCableEquFragment";

    private String tag;
    Map<String, List> searchMap;

    public CablemanageEvent(Map<String, List> searchMap, String tag) {
        this.searchMap = searchMap;
        this.tag = tag;
    }

    public Map<String, List> getSearchMap() {
        return searchMap;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
