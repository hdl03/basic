package com.aridity.basic.utils;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class HelperLoad {

    public static void initLoad() {
        Class<?>[] clsList = {
                ClassHelper.class,
                BeanUtils.class,
                IocUtils.class,
                ControllerHelper.class
        };
        for (Class<?> cls : clsList) {
            ClassUtils.loadClass(cls);

        }
    }

}
