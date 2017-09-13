package com.aridity.basic.utils;

import java.lang.annotation.*;

/**
 * Created by shanlin on 2017/9/13.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {
}
