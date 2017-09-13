package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shanlin on 2017/8/31.
 * 加载所有的类
 */
public final class ClassHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ClassHelper.class);
    private static final Set<Class<?>> SETS;

    static {
        SETS = ClassUtils.getClassSet(ConfigHelper.getBasePackage());
    }

    public static Set<Class<?>> getClassSets() {
        return SETS;
    }

    public static Set<Class<?>> getServiceSets() {
        Set<Class<?>> services = new HashSet<>();
        Set<Class<?>> classes = getClassSets();
        for (Class<?> class2 : classes) {
            if (class2.isAnnotationPresent(Service.class)) {
                services.add(class2);
            }
        }
        return services;
    }

    public static Set<Class<?>> getControllerSets() {
        Set<Class<?>> services = new HashSet<>();
        Set<Class<?>> classes = getClassSets();
        for (Class<?> class2 : classes) {
            if (class2.isAnnotationPresent(Controller.class)) {
                services.add(class2);
            }
        }
        return services;
    }

    public static Set<Class<?>> getBeanSets() {
        Set<Class<?>> beans = new HashSet<>();
        beans.addAll(getControllerSets());
        beans.addAll(getServiceSets());
        return beans;
    }

    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> beans = new HashSet<>();
        Set<Class<?>> classes = getClassSets();
        for (Class<?> class2 : classes) {
            if (superClass.isAssignableFrom(class2) && !superClass.equals(class2)) {
                beans.add(class2);
            }
        }
        LOG.info("获取子类 {}", beans);
        return beans;
    }

    public static Set<Class<?>> getClassSetByAnnotion(Class<? extends Annotation> annotionClass) {
        Set<Class<?>> beans = new HashSet<>();
        Set<Class<?>> classes = getClassSets();
        for (Class<?> class2 : classes) {
            if (class2.isAnnotationPresent(annotionClass)) {
                beans.add(class2);
            }
        }
        return beans;
    }


}
