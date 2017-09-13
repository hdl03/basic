package com.aridity.basic.aop;

/**
 * Created by shanlin on 2017/9/7.
 */
public class HelloImpl implements Hello {
    @Override
    public void say() {
        System.out.println("测试hello world");
    }
}
