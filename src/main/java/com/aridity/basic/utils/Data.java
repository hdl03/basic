package com.aridity.basic.utils;

/**
 * Created by shanlin on 2017/8/31.
 */
public class Data extends BaseBean{
    private Object value;

    public Data(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
