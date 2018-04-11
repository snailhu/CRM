package com.zifangdt.ch.product.validator;

import com.zifangdt.ch.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ProductExistValidator implements ConstraintValidator<ProductExist, Long> {
    @Autowired
    ProductService productService;

    @Override
    public void initialize(ProductExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (productService.exists(value)){
            return true;
        } else {
            return false;
        }
    }
}
