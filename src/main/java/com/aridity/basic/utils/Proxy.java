package com.aridity.basic.utils;

/**
 * Created by shanlin on 2017/9/7.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
