package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoad() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInit
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInit) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInit, getClassLoad());
        } catch (ClassNotFoundException classNotFound) {
            LOGGER.error("类查找不到", classNotFound);
        }
        return cls;
    }

    /**
     * 加载类
     *
     * @param className
     * @param
     * @return
     */
    public static void loadClass(Class<?> className) {
        try {
            getClassLoad().loadClass(className.getName());
        } catch (ClassNotFoundException classNotFound) {
            LOGGER.error("类查找不到", classNotFound);
        }
    }

    /**
     * 获取包名下，类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet(String basePackage) {
        Set<Class<?>> sets = new HashSet<>();
        try {
            //
            Enumeration<URL> enumeration = getClassLoad().getResources(basePackage.replace(".", "/"));

            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String basePackagePath = url.getPath().replaceAll("%20", " ");
                    addClass(basePackagePath, basePackage);
                } else if ("jar".equals(url.getProtocol())) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (null != jarURLConnection) {
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if (null != jarFile) {
                            String entryName = jarFile.getName();
                            if (entryName.endsWith(".class")) {
                                String className = entryName.substring(0, entryName.lastIndexOf(".")).replaceAll("/", ".");
                                sets.add(loadClass(className, false));
                            }

                        }
                    }
                }

            }

        } catch (IOException ioException) {
            LOGGER.error("IO 异常", ioException);
        }
        return sets;

    }


    public static Set<Class<?>> addClass(String basePackagePath, String basePackage) {
        Set<Class<?>> sets = new HashSet<>();
        File[] files = new File(basePackagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });

        for (File file : files) {

            if (file.isFile()) {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                className = basePackage + className;
                sets.add(loadClass(className, false));
            } else {
                String subPackagePath = file.getName();
                basePackagePath = subPackagePath + "/" + subPackagePath;
                basePackage = basePackage + "." + subPackagePath;
                addClass(basePackagePath, basePackage);
            }
        }
        return sets;
    }


}
