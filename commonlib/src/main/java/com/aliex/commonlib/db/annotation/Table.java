package com.aliex.commonlib.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: Aliex <br/>
 * created on: 2017/3/3 <br/>
 * description: <br/>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String name();

    String onCreated() default "";
}
