package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.enums.DataEnum;

import java.lang.annotation.*;

/**
 * Created by 袁兵 on 2017/9/12.
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NameGenerator {
    DataEnum[] value() default {};
}
