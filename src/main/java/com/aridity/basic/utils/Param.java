package com.aridity.basic.utils;

import java.util.Map;

/**
 * Created by shanlin on 2017/8/31.
 */
public class Param extends BaseBean{
    private Map<String, Object> map;


    public Param(Map<String, Object> map) {
        this.map = map;
    }


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
