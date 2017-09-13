package com.aridity.basic.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shanlin on 2017/9/7.
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }

    public DynamicProxy() {
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(object, args);
        after();
        return result;
    }


    public void before() {
        System.out.println("before");
    }

    public void after() {
        System.out.println("after");
    }


}
