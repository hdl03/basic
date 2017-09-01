package com.aridity.basic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by shanlin on 2017/9/1.
 */
public final class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objMapper = new ObjectMapper();

    public static <T> String toJson(T obj) {
        String json = null;
        try {
            json = objMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("json 转化错误", e);
        }
        return json;
    }

    public static <T> T fromJson(Class<T> cls, String json) {
        T obj = null;
        try {
            obj = objMapper.readValue(json, cls);
        } catch (IOException e) {
            LOG.error("json 转化错误", e);
        }
        return obj;
    }

}
