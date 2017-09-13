package com.aridity.basic.aop;

/**
 * Created by shanlin on 2017/9/7.
 */
public class HelloProxy implements Hello {
    private Hello hello;

    public HelloProxy() {
        this.hello = new HelloImpl();
    }

    public Hello getHello() {
        return hello;
    }

    @Override
    public void say() {
        before();
        hello.say();
        after();
    }

    public void before() {
        System.out.println("before");
    }

    public void after() {
        System.out.println("after");
    }
}
