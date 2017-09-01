package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by shanlin on 2017/8/29.
 */
public final class PropsUtils {

    private static final Logger LOGGEG = LoggerFactory.getLogger(PropsUtils.class);

    public static Properties loadProps(String fileName){
        Properties properties = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

            if (null == inputStream) {
                throw new FileNotFoundException("文件没有找到");
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException io) {
            LOGGEG.error("导入文件失败", io);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            }catch (IOException io){
                LOGGEG.error("关闭流失败",io);
            }
        }
        return properties;
    }


    public static String getString(Properties properties,String key){

        return getString(properties,key,"");

    }

    public static String getString(Properties properties,String key,String defaultValue){
        String value = defaultValue;
        if(properties.containsKey(key)){
            value =  properties.getProperty(key);
        }
        return value;
    }

}
