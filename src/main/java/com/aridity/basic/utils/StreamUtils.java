package com.aridity.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shanlin on 2017/9/1.
 */
public final class StreamUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

    public static String getString(InputStream inputStream) {

        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                builder.append(line);
            }
        } catch (IOException ioException) {
            LOGGER.error("流处理失败");
        }
        return builder.toString();
    }
}
