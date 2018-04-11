package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class OrganizationHasBossValidator implements ConstraintValidator<OrganizationHasBoss, Long> {
    @Autowired
    UaaServerApi uaaServerApi;

    @Override
    public void initialize(OrganizationHasBoss constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            Organization organization = uaaServerApi.getOrganization(value);
            return organization != null && organization.getHeadId() != null;
        }
    }
}
