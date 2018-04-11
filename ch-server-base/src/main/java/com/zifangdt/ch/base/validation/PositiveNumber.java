package com.zifangdt.ch.base.validation;

import javax.validation.constraints.DecimalMin;

/**
 * Created by 袁兵 on 2017/10/11.
 */
@DecimalMin(value = "0", inclusive = false, message = "必须大于0")
public @interface PositiveNumber {
}
