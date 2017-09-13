package com.aridity.basic.utils;

import java.lang.annotation.*;

/**
 * Created by shanlin on 2017/9/7.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
