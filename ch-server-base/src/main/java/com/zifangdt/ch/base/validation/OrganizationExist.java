package com.zifangdt.ch.base.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrganizationExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrganizationExist {
    String message() default "部门不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
