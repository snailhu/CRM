package com.zifangdt.ch.market.validation;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.market.bo.UserOrga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class UserOrgaExistValidator implements ConstraintValidator<UserOrgaExist, List<UserOrga>> {
    @Autowired
    UaaServerApi uaaServerApi;

    @Override
    public void initialize(UserOrgaExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<UserOrga> lst, ConstraintValidatorContext context) {
        if (lst == null){
            return true;
        }
        for (UserOrga uo: lst){
            if (uo.isUser) {
                if (uo.userId != null) {
                    User user = uaaServerApi.getUser(uo.userId);
                    if (user != null){
                        continue;
                    }
                }
            } else {
                if (uo.orgaId != null) {
                    Organization organization = uaaServerApi.getOrganization(uo.orgaId);
                    if (organization != null) {
                        continue;
                    }
                }
            }
            return false;
        }
        return true;
    }
}
