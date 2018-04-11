package com.zifangdt.ch.product.validator;

import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.product.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RepoExistValidator implements ConstraintValidator<RepoExist, Long> {
    @Autowired
    RepoService repoService;

    @Override
    public void initialize(RepoExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (repoService.exists(value)){
            Repo repo = repoService.get(value);
            if (repo.getIsValid()){
                return true;
            }
        }
        return false;
    }
}
