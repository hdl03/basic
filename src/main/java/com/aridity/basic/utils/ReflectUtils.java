package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class ReflectUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtils.class);

    public static Object newInstance(Class<?> cls) {
        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("初始化失败", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("初始化失败", e);
        }
        return obj;
    }

    public static Object invokeMethod(Object obj, Method method, Object... params) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, params);
        } catch (IllegalAccessException e) {
            LOGGER.error("初始化失败", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("初始化失败", e);
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("初始化失败", e);
        }
    }

}
