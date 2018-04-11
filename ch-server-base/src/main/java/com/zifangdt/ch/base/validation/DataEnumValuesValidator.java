package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.enums.DataEnum;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class DataEnumValuesValidator implements ConstraintValidator<DataEnumValue, Object> {
    private DataEnum dataEnum;
    private boolean checkExclusion;

    @Override
    public void initialize(DataEnumValue constraintAnnotation) {
        dataEnum = constraintAnnotation.value();
        checkExclusion = constraintAnnotation.checkExclusion();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof Long) {
            return dataEnum.getValue((Long) value) != null;
        } else if (value instanceof List) {
            List<Long> list = (List<Long>) value;
            if (list.isEmpty()) {
                return true;
            }
            for (Long key : list) {
                if (dataEnum.getValue(key) == null) {
                    return false;
                }
            }
            if (checkExclusion) {
                Set<Long> exclusions = dataEnum.getExclusions();
                if (!CollectionUtils.isEmpty(exclusions)) {
                    HashSet<Long> ids = new HashSet<>(exclusions);
                    ids.retainAll(list);
                    if (ids.size() > 1) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("存在互斥值" + ids).addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

}
