package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by shanlin on 2017/8/31.
 *
 * 动态注入
 */
public final class IocUtils {

    private static final Logger LOG = LoggerFactory.getLogger(IocUtils.class);

    static {
        Map<Class<?>, Object> classObjectMap = BeanUtils.getBeanMap();
        LOG.debug("IOC 获取初始Bean {}");
        for (Map.Entry<Class<?>, Object> cls : classObjectMap.entrySet()) {
            Class<?> key = cls.getKey();
            Object obj = cls.getValue();
            LOG.debug("IOC 记载类 {} ，{}", key, obj);
            Field[] fields = key.getDeclaredFields();
            if (null != fields && fields.length > 0) {
                LOG.debug("IOC 类属性数量 {} ", fields.length);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Class<?> beanFieldClass = field.getType();
                        Object beanObj = BeanUtils.getBean(beanFieldClass);
                        LOG.info("IOC 设置属性 {} ，{}", beanFieldClass, beanObj);
                        // 通过反射，设置属性的值
                        ReflectUtils.setField(obj, field, beanObj);
                    }
                }

            }
        }
    }
}
