package com.aridity.basic.utils;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shanlin on 2017/8/29.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //    private static final String DRIVER;
//    private static final String URL;
//    private static final String USERNAME;
//    private static final String PASSWORD;
    private static final BasicDataSource DATASOURCE = new BasicDataSource();

    static {

//        Properties properties = PropsUtils.loadProps("config.properties");
//
//        DRIVER = PropsUtils.getString(properties, "jdbc.driver");
//        URL = PropsUtils.getString(properties, "jdbc.url");
//        USERNAME = PropsUtils.getString(properties, "jdbc.username");
//        PASSWORD = PropsUtils.getString(properties, "jdbc.password");
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException classNot) {
//            LOGGER.error("没有找到jdbc 驱动");
//        }
        DATASOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
        DATASOURCE.setUrl(ConfigHelper.getJdbcUrl());
        DATASOURCE.setUsername(ConfigHelper.getJdbcUsername());
        DATASOURCE.setPassword(ConfigHelper.getJdbcPassword());
    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        try {
            if (null == CONNECTION_THREAD_LOCAL.get()) {
                connection = DATASOURCE.getConnection();//DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }

        } catch (SQLException sql) {
            LOGGER.error("获取数据库连接失败", sql);
        } finally {
            CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }


    public static void closeConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        try {
            if (null == connection || !connection.isClosed()) {
                //connection.close();
            }
        } catch (SQLException sql) {
            LOGGER.error("关闭数据库连接失败", sql);
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entity = null;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException sqlException) {
            LOGGER.error("查询数据失败", sqlException);
        } finally {
            closeConnection();
        }
        return entity;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), params);
            LOGGER.info("打印查询出来的结果 {}", entity);
        } catch (SQLException sqlException) {
            LOGGER.error("查询数据失败", sqlException);
        } finally {
            closeConnection();
        }
        return entity;
    }

    public static <T> Boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {

        if (null == fieldMap || fieldMap.isEmpty()) {
            LOGGER.info("插入数据库属性为空");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?").append(",");
        }
        columns.deleteCharAt(columns.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");

        sql = sql + columns + "VALUES" + values;
        LOGGER.info("打印sql 语句 {}", sql);
        return 1 == executeUpdate(sql, fieldMap.values().toArray());
    }


    public static <T> Boolean updateEntity(Class<T> entityClass, Object id, Map<String, Object> fieldMap) {

        if (null == fieldMap || fieldMap.isEmpty()) {
            LOGGER.info("修改数据库属性为空");
            return false;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("update " + getTableName(entityClass) + " set ");

        for (String fieldName : fieldMap.keySet()) {
            sql.append(fieldName).append(" = ").append("?").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where id = ?");
        Map<String,Object> map = new LinkedHashMap<>();
        map.putAll(fieldMap);
        map.put("id", id);
        LOGGER.info("打印sql 语句 {} 和 值 {}", sql.toString(),map.values().toArray());
        return 1 == executeUpdate(sql.toString(), map.values().toArray());
    }


    public static <T> Boolean deleteEntity(Class<T> entityClass, Map<String, Object> fieldMap) {

        if (null == fieldMap || fieldMap.isEmpty()) {
            LOGGER.info("删除数据库属性为空");
            return false;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("delete from " + getTableName(entityClass) + " where ");
        for (String fieldName : fieldMap.keySet()) {
            sql.append(fieldName).append(" = ").append("?").append(" and");
        }
        LOGGER.info("打印sql 语句 {}", sql.toString());
        sql.delete(sql.length() - 4, sql.length());
        LOGGER.info("打印sql 语句 {}", sql.toString());
        return 1 == executeUpdate(sql.toString(), fieldMap.values().toArray());
    }

    private static <T> String getTableName(Class<T> entityClass) {
        TableName tableName = entityClass.getAnnotation(TableName.class);
        return tableName.name();

    }

    private static int executeUpdate(String sql, Object... params) {
        try {
            return QUERY_RUNNER.execute(getConnection(), sql, params);
        } catch (SQLException sqlException) {
            LOGGER.error("执行sql 语句异常", sqlException);
        } finally {
            closeConnection();
        }
        return 0;

    }
}
