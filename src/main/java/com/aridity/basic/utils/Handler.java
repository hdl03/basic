package com.aridity.basic.utils;

import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/8/31.
 */
public class Handler extends BaseBean{
    private Class<?> controller;
    private Method method;

    public Handler(Class<?> controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Class<?> getController() {
        return controller;
    }

    public void setController(Class<?> controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
