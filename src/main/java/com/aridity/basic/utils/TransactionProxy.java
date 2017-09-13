package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/9/12.
 */
public class TransactionProxy implements Proxy {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean> BOOLEAN_THREAD_LOCAL = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Method method = proxyChain.getMethod();
        if (!BOOLEAN_THREAD_LOCAL.get() && method.isAnnotationPresent(Transaction.class)) {
            BOOLEAN_THREAD_LOCAL.set(true);
            try {
                LOG.info("开启事物");
                DatabaseHelper.beginTarnsaction();
                LOG.info("执行操作");
                result = proxyChain.doProxyChain();
                LOG.info("结束事物");
                DatabaseHelper.closeConnection();
            } catch (Exception ex) {
                LOG.error("事物回滚", ex);
                DatabaseHelper.rollback();
                throw new RuntimeException(ex);
            } finally {
                BOOLEAN_THREAD_LOCAL.remove();
            }
        } else {
            LOG.info("没有事物开启操作");
            result = proxyChain.doProxyChain();
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
