package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by shanlin on 2017/9/1.
 */
public final class CodeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CodeUtils.class);

    public static String encodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("不支持的类型", e);
        }
        return target;
    }

    public static String decodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("不支持的类型", e);
        }
        return target;
    }


}
