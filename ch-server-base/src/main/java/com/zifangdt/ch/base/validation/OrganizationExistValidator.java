package com.zifangdt.ch.base.validation;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class OrganizationExistValidator implements ConstraintValidator<OrganizationExist, Long> {

    @Autowired
    UaaServerApi uaaServerApi;

    @Override
    public void initialize(OrganizationExist organizationExist) {
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        if (aLong == null) return false;
        Organization organization = uaaServerApi.getOrganization(aLong);
        return organization != null;
    }
}
