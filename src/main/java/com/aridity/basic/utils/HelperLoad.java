package com.aridity.basic.utils;

/**
 * Created by shanlin on 2017/8/31.
 *
 * 初始化记载
 */
public final class HelperLoad {

    public static void initLoad() {
        Class<?>[] clsList = {
                ClassHelper.class,// 加载所有类
                BeanUtils.class, // 获取所有Service 和 Controller
                AopHelper.class,// 记载aop 配置
                IocUtils.class, // 反射设置属性值
                ControllerHelper.class // 获取Controller 下的方法

        };
        for (Class<?> cls : clsList) {
            ClassUtils.loadClass(cls);
        }
    }

}
