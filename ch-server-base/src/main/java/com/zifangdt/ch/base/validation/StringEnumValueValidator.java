package com.zifangdt.ch.base.validation;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class StringEnumValueValidator implements ConstraintValidator<StringEnumValue, String> {
    private String[] values;

    @Override
    public void initialize(StringEnumValue constraintAnnotation) {
        values=constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (values != null && values.length > 0) {
            return ArrayUtils.contains(values, value);
        }
        return true;
    }

}
