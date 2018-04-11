package com.zifangdt.ch.base.validation;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class IntegerEnumValueValidator implements ConstraintValidator<IntegerEnumValue, Object> {
    private int[] values;

    @Override
    public void initialize(IntegerEnumValue constraintAnnotation) {
        values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return (values == null || values.length == 0) || ArrayUtils.contains(values, Integer.parseInt(value.toString()));
    }
}
