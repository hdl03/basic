package com.aridity.basic.aop;

/**
 * Created by shanlin on 2017/9/7.
 */
public class Test {
    public static void main(String[] args) {
        // Hello hello = new HelloProxy();
        //hello.say();


        //Hello proxy = new DynamicProxy(new HelloImpl()).getProxy();
        // proxy.say();
        Hello hello = CGLibProxy.getInstance().getProxy(HelloImpl.class);
        hello.say();
    }


}
