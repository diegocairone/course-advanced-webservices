package com.cairone.rest.ctrl.validator;

import com.cairone.rest.ctrl.constraint.PageConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public class PageValidator implements ConstraintValidator<PageConstraint, Pageable> {

    List<String> allowedSortParameters;
    int maxPageSize;

    @Override
    public void initialize(PageConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxPageSize = constraintAnnotation.maxPageSize();
        this.allowedSortParameters = Arrays.asList(constraintAnnotation.fields());
    }

    @Override
    public boolean isValid(Pageable pageable, ConstraintValidatorContext constraintValidatorContext) {
        boolean allowed = pageable.getSort().stream()
                .allMatch(sort -> allowedSortParameters.contains(sort.getProperty()));
        return pageable.getPageSize() <= maxPageSize && allowed;
    }
}
