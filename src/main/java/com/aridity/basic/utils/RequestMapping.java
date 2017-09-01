package com.aridity.basic.utils;

/**
 * Created by shanlin on 2017/8/30.
 */
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME )
public @interface RequestMapping {
    /**
     *
     * url
     */
    String name();
}
