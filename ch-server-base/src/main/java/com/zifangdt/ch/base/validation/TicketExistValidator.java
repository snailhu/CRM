package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.dao.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TicketExistValidator implements ConstraintValidator<TicketExist, Long>{

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void initialize(TicketExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        BaseMapper mapper = (BaseMapper) applicationContext.getBean("ticketMapper");
        return mapper.existsWithPrimaryKey(value);
    }
}
