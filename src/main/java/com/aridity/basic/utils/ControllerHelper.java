package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shanlin on 2017/8/31.
 *
 *
 */
public final class ControllerHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerHelper.class);
    private static final Map<Request, Handler> CONTROLLERS = new HashMap<>();


    static {
        Set<Class<?>> classes = ClassHelper.getControllerSets();
        if (null != classes) {
            LOG.info("获取所有的Controller {}", classes.size());
            for (Class<?> cls : classes) {
                Method[] methods = cls.getDeclaredMethods();
                if (null != methods && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String name = requestMapping.name();
                            if (name.matches("\\w+:/\\w*")) {
                                String[] strs = name.split(":");
                                if (null != strs && strs.length == 2) {
                                    String requestMethod = strs[0];
                                    String requestPath = strs[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(cls, method);
                                    CONTROLLERS.put(request, handler);
                                    LOG.info("添加请求 {} 和 处理 {}", request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<Request, Handler> getControllers() {
        return CONTROLLERS;
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return CONTROLLERS.get(request);
    }


}
