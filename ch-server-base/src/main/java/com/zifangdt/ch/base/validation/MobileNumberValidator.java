package com.zifangdt.ch.base.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {
    private static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^1[34578]\\d{9}$");
    @Override
    public void initialize(MobileNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if(value.length()!=11){
            return false;
        }
        return MOBILE_NUMBER_PATTERN.matcher(value).matches();
    }
}
