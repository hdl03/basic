package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/9/12.
 */
public class AspectProxy implements Proxy {
    private static final Logger LOG = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method method = proxyChain.getMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(targetClass, method, params)) {
                before(targetClass, method, params);
                result = proxyChain.doProxyChain();
                after(targetClass, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception ex) {
            LOG.error("异常", ex);
            error(targetClass, method, params);
            throw ex;
        } finally {
            end();
        }


        return result;
    }

    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] params) throws Throwable {

    }


    public void end() {

    }


}
