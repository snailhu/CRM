package com.zifangdt.ch.base.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Documented
@Constraint(validatedBy = IntegerEnumValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerEnumValue {
    int[] value() default {};

    String message() default "不在枚举值范围之内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
