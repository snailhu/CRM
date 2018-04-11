package com.zifangdt.ch.product.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RepoExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepoExist {
    String message() default "仓库不存在或仓库没有启动";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
