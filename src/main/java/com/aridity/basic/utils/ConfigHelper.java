package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by shanlin on 2017/8/31.
 */
public final class ConfigHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);
    private static final Properties properties = PropsUtils.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtils.getString(properties, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtils.getString(properties, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtils.getString(properties, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtils.getString(properties, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getBasePackage() {
        return PropsUtils.getString(properties, ConfigConstant.BASE_PACKAGE);
    }

    public static String getJspPath() {
        return PropsUtils.getString(properties, ConfigConstant.JSP_PATH);
    }

    public static String getAssetPath() {
        return PropsUtils.getString(properties, ConfigConstant.ASSET_PATH);
    }

}
