package com.aridity.basic.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class ClassHelper {
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


}
