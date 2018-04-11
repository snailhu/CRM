package com.zifangdt.ch.base.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TicketExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TicketExist {
    String message() default "产品不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
