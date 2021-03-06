package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.enums.ConfigType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Documented
@Constraint(validatedBy = ConfiguredOptionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfiguredOption {

    ConfigType[] value();

    String message() default "不在可用的选项范围之内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
