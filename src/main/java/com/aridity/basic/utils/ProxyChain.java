package com.aridity.basic.utils;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shanlin on 2017/9/7.
 */
public class ProxyChain extends BaseBean {
    private final Class<?> targetClass;
    private final Object target;
    private final Method method;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxies = new ArrayList<>();

    private int index = 0;


    public ProxyChain(Class<?> targetClass, Object target, Method method, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxies) {
        System.out.println("初始化proxyChain");
        this.targetClass = targetClass;
        this.target = target;
        this.method = method;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxies = proxies;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxies.size() > index) {
            System.out.println("index :" + index);
            result = proxies.get(index++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(target, methodParams);
        }
        return result;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
