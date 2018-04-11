package com.zifangdt.ch.base.enums.pair;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by 袁兵 on 2017/9/6.
 */
public interface IntVerifierEnum {

    String DEFAULT_FIELD = "intVerifier";

    default int getIntVerifier() {
        Field field = ReflectionUtils.findField(getClass(), DEFAULT_FIELD);
        if (field == null)
            throw new RuntimeException("enum that implements IntVerifierEnum must have a field named \"intVerifier\"");
        field.setAccessible(true);
        try {
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static <T extends Enum<T>> T fromIntVerifier(Class<T> enumClass, int intVerifier) {
        if (!IntVerifierEnum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(enumClass + " must implement IntVerifierEnum");
        }
        T[] enums = enumClass.getEnumConstants();
        for (T t : enums) {
            if (((IntVerifierEnum) t).getIntVerifier() == intVerifier)
                return t;
        }
        throw new IllegalArgumentException("cannot parse int " + intVerifier + " to " + enumClass.getName());
    }
}
