package cn.droidlover.xdroidmvp.sys.event.supertension;

import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.event.IBus;

/**
 * Created by ronaldo on 2017/7/15.
 */

public class SearchEvent implements IBus.IEvent {

    Map<String, List> searchMap;

    public SearchEvent(Map<String, List> searchMap) {
        this.searchMap = searchMap;
    }

    public Map<String, List> getSearchMap() {
        return searchMap;
    }

    @Override
    public int getTag() {
        return 0;
    }
}
