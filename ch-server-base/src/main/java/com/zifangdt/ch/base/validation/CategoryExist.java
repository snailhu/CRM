package com.zifangdt.ch.base.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CategoryExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryExist {

    int[] value() default {};

    String message() default "类别不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
