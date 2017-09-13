package com.aridity.basic.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by shanlin on 2017/9/7.
 */
public class ProxyManager {
    private static final Logger LOG = LoggerFactory.getLogger(ProxyManager.class);

    public static Object createProxy(final Class<?> targetClass, List<Proxy> proxyList) {
        return Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, method, methodProxy, objects, proxyList).doProxyChain();
            }
        });

    }

}
