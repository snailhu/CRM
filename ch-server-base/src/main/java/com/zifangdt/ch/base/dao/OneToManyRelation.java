package com.zifangdt.ch.base.dao;

import java.lang.annotation.*;

/**
 * Created by 袁兵 on 2017/9/12.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OneToManyRelation {
    String value() default "";
}
