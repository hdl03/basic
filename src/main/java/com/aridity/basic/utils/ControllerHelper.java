package com.aridity.basic.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class ControllerHelper {
    private static final Map<Request, Handler> CONTROLLERS = new HashMap<>();

    static {

        Set<Class<?>> classes = ClassHelper.getControllerSets();

        for (Class<?> cls : classes) {
            Method[] methods = cls.getDeclaredMethods();
            if (null != methods && methods.length > 0) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String name = requestMapping.name();
                        //TODO 正则校验
                        if (name.matches("\\w+:/\\w*")) {
                            String[] strs = name.split(":");
                            if (null != strs && strs.length == 2) {
                                String requestMethod = strs[0];
                                String requestPath = strs[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(cls, method);
                                CONTROLLERS.put(request, handler);
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
