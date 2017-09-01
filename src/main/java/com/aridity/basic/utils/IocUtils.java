package com.aridity.basic.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class IocUtils {

    static {

        Map<Class<?>, Object> classObjectMap = BeanUtils.getBeanMap();
        for (Map.Entry<Class<?>, Object> cls : classObjectMap.entrySet()) {
            Class<?> key = cls.getKey();
            Object obj = cls.getValue();
            Field[] fields = key.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> beanFieldClass = field.getType();
                    Object beanObj = BeanUtils.getBean(beanFieldClass);
                    // 通过反射，设置属性的值
                    ReflectUtils.setField(obj, field, beanObj);
                }
            }

        }

    }
}
