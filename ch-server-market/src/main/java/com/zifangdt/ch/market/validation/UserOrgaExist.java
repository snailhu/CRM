package com.zifangdt.ch.market.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserOrgaExistValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserOrgaExist {
    String message() default "用户或部门不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
