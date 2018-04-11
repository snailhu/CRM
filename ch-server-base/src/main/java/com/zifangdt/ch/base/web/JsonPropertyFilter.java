package com.zifangdt.ch.base.web;

import java.lang.annotation.*;

/**
 * Created by 袁兵 on 2018/1/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(JsonPropertyFilters.class)
public @interface JsonPropertyFilter {
    String[] value() default {};

    Class<?> entityClass() default Void.class;
}
