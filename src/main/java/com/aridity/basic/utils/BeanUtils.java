package com.aridity.basic.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shanlin on 2017/8/31.
 * <p>
 * 获取所有的Controller 和 Service
 */
public final class BeanUtils {

    private static final Map<Class<?>, Object> BEANS = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanSets();
        for (Class<?> cls : classSet) {
            Object obj = ReflectUtils.newInstance(cls);
            BEANS.put(cls, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEANS;
    }

    public static <T> T getBean(Class<T> cls) {
        if (BEANS.containsKey(cls)) {
            return (T) BEANS.get(cls);
        } else {
            throw new RuntimeException("没有实例化");
        }
    }

    public static void setBeans(Class<?> cls, Object target) {
       // if (!BEANS.containsKey(cls)) {
            BEANS.put(cls, target);
      //  }

    }
}
