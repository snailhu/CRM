package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.dao.BaseMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CategoryExistValidator implements ConstraintValidator<CategoryExist, Long>{
    @Override
    public void initialize(CategoryExist constraintAnnotation) {

    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        BaseMapper mapper = (BaseMapper) applicationContext.getBean("categoryMapper");
        return mapper.existsWithPrimaryKey(value);
    }
}
