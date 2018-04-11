package com.zifangdt.ch.product.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductExist {
    String message() default "产品不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
