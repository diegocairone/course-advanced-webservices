package com.cairone.rest.ctrl.validator;

import com.cairone.rest.ctrl.constraint.SortByConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class SortByValidator implements ConstraintValidator<SortByConstraint, String> {

    List<String> allowedSortParameters;

    @Override
    public void initialize(SortByConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.allowedSortParameters = Arrays.asList(constraintAnnotation.fields());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return allowedSortParameters.contains(value);
    }
}
