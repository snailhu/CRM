package com.zifangdt.ch.base.enums.pair;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by 袁兵 on 2018/1/10.
 */
public interface NamedEnum {
    String DEFAULT_FIELD = "name";

    default String getName() {
        Field field = ReflectionUtils.findField(getClass(), DEFAULT_FIELD);
        if (field == null)
            throw new RuntimeException("enum that implements NamedEnum must have a field named \"name\"");
        field.setAccessible(true);
        try {
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
