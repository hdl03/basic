package com.aridity.basic.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/9/7.
 */
public class CGLibProxy implements MethodInterceptor {

    //private Object object;
    private static final CGLibProxy INSTANCE = new CGLibProxy();

    private CGLibProxy() {
        //this.object = object;
    }

    public static CGLibProxy getInstance() {
        return INSTANCE;
    }


    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
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
