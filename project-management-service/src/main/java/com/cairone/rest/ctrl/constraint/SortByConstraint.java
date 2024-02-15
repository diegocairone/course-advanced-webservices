package com.cairone.rest.ctrl.constraint;

import com.cairone.rest.ctrl.validator.SortByValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortByValidator.class)
public @interface SortByConstraint {
    String message() default "Invalid sort-by value";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String[] fields() default {};
}
