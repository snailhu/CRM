package com.zifangdt.ch.base.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExist {
    String message() default "用户不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
