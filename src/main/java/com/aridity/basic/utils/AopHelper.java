package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by shanlin on 2017/9/12.
 */
public class AopHelper {
    private static final Logger LOG = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            LOG.info("打印开始");
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            LOG.info("打印开始1");
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            LOG.info("打印开始2: {}", targetMap.size());
            for (Map.Entry<Class<?>, List<Proxy>> target : targetMap.entrySet()) {
                Class<?> targetClass = target.getKey();
                List<Proxy> proxies = target.getValue();
                LOG.info("打印开始3 {}", targetClass.getName());
                Object obj = ProxyManager.createProxy(targetClass, proxies);
                LOG.info("打印开始4 {} ,{}", targetClass.getName(), obj);
                BeanUtils.setBeans(targetClass, obj);
            }

        } catch (Exception ex) {
            LOG.error("aop 初始化异常", ex);
        }


    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClass = new HashSet<>();
        Class<? extends Annotation> cls = aspect.value();
        if (null != cls && !cls.isAnnotationPresent(Aspect.class)) {
            targetClass.addAll(ClassHelper.getClassSetByAnnotion(cls));
        }
        LOG.info("创建目标类 {}", targetClass);
        return targetClass;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        proxyMap.putAll(addAspectProxy());
        proxyMap.putAll(addTransactionProxy());
        LOG.info("加载所有代理类 {}", proxyMap);
        return proxyMap;
    }

    private static Map<Class<?>, Set<Class<?>>> addAspectProxy() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> classSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = cls.getAnnotation(Aspect.class);
                Set<Class<?>> set = createTargetClassSet(aspect);
                proxyMap.put(cls, set);
            }
        }
        LOG.info("加载所有aspect {}", proxyMap);
        return proxyMap;
    }

    private static Map<Class<?>, Set<Class<?>>> addTransactionProxy() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> classSet = ClassHelper.getClassSetByAnnotion(Service.class);
        proxyMap.put(TransactionProxy.class, classSet);
        LOG.info("加载所有 transaction {}", proxyMap);
        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> setEntry : proxyMap.entrySet()) {
            Class<?> cls = setEntry.getKey();
            Set<Class<?>> classSet = setEntry.getValue();
            for (Class<?> set : classSet) {
                Proxy proxy = (Proxy) cls.newInstance();
                if (targetMap.containsKey(set)) {
                    targetMap.get(set).add(proxy);
                } else {
                    List<Proxy> proxies = new ArrayList<>();
                    proxies.add(proxy);
                    targetMap.put(set, proxies);
                }
            }

        }
        LOG.info("加载所有目标类 {}", proxyMap);
        return targetMap;
    }


}
