package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/9/12.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);
    private long beignTime;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOG.info("---------------begin------------------");
        LOG.info("调用的类： {}", cls.getName());
        LOG.info("调用的方法： {}", method.getName());
        beignTime = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOG.info("使用时间： {}", System.currentTimeMillis() - beignTime);
        LOG.info("---------------end------------------");
    }
}
