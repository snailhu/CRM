package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistValidator implements ConstraintValidator<UserExist, Long> {

    @Autowired
    UaaServerApi uaaServerApi;

    @Override
    public void initialize(UserExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true;
        User user = uaaServerApi.getUser(value);
        return user != null;
    }
}
