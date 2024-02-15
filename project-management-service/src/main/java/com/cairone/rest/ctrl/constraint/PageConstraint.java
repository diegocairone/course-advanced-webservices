package com.cairone.rest.ctrl.constraint;

import com.cairone.rest.ctrl.validator.PageValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PageValidator.class)
public @interface PageConstraint {
    String message() default "Invalid page parameters";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String[] fields() default {};
    int maxPageSize() default 100;
}
