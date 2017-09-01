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

    private static Set<Class<?>> sets = new HashSet<>();

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
            throw new RuntimeException("类查找不到", classNotFound);
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
            Class.forName(className.getName(), true, getClassLoad());
        } catch (ClassNotFoundException classNotFound) {
            LOGGER.error("类查找不到", classNotFound);
            throw new RuntimeException("类查找不到", classNotFound);
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
            //获取指定包下的路径
            Enumeration<URL> enumeration = getClassLoad().getResources(basePackage.replace(".", "/"));
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                String protocol = url.getProtocol();
                LOGGER.debug("获取到的路径 ： {}", url);
                // 文件处理
                if ("file".equals(protocol)) {
                    // 处理空格
                    String basePackagePath = url.getPath().replaceAll("%20", " ");
                    LOGGER.debug("基础路径 {}", basePackagePath);
                    sets.addAll(addClass(basePackagePath, basePackage));
                } else if ("jar".equals(url.getProtocol())) {
                    // jar 包处理
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
            throw new RuntimeException("IO 异常", ioException);
        }
        LOGGER.debug("收集到的类大小{}", sets.size());
        return sets;

    }


    //加载指定路径下，所有的类
    public static Set<Class<?>> addClass(final String basePackagePath, final String basePackage) {
        LOGGER.debug("路径 ：{} ， 包名 {} 。", basePackagePath, basePackage);
        File[] files = new File(basePackagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        // 指定包下的路径不为空，加载class 类
        if (null != files && files.length > 0) {
            LOGGER.debug("指定路径下的的类数量 {}", files.length);
            for (File file : files) {
                if (file.isFile()) {
                    // 文件，加载类
                    LOGGER.debug("加载类{}", file.getName());
                    String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                    className = basePackage + "." + className;
                    LOGGER.debug("加载类{},类的完整路径 {}", file.getName(), className);
                    sets.add(loadClass(className, false));
                } else {
                    // 是路径，递归调用
                    LOGGER.debug("是路径，不是文件");
                    String subPackagePath = file.getName();
                    String packagePath = basePackagePath + "/" + subPackagePath;
                    String packageName = basePackage + "." + subPackagePath;
                    addClass(packagePath, packageName);
                }
            }
        }
        return sets;
    }


}
