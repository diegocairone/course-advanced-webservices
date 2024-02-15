package com.cairone.rest.ctrl;

import com.cairone.error.AppClientException;
import com.cairone.core.exception.ResourceNotFoundException;
import com.cairone.rest.resource.ErrorResource;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class AppAdviceCtrl {

    @ExceptionHandler(AppClientException.class)
    public ResponseEntity<ErrorResource> handleBadRequestExceptions(AppClientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResource.builder()
                .withMessage(ex.getMessage())
                .withReason("Invalid request")
                .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResource> handleNotFoundExceptions(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResource.builder()
                .withMessage(ex.getMessage())
                .withReason("Resource not found")
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResource> handleValidationExceptions(ConstraintViolationException ex) {

        ErrorResource errorResource = ErrorResource.builder()
                .withMessage("At least one field in the request is invalid")
                .withReason("One or more fields are syntactically incorrect")
                .build();

        ex.getConstraintViolations().forEach(constraintViolation ->
                errorResource.addError(
                        constraintViolation.getMessageTemplate(),
                        constraintViolation.getInvalidValue().toString()));

        return ResponseEntity.badRequest().body(errorResource);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResource> handleValidationExceptions(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ErrorResource.builder()
                .withMessage("Invalid arguments")
                .withReason(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResource> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ErrorResource errorResource = ErrorResource.builder()
                .withMessage("At least one field in the request is invalid")
                .withReason("One or more fields are syntactically incorrect")
                .build();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorResource.addError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorResource);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResource> handleNotFoundExceptions(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResource.builder()
                .withMessage("Invalid arguments")
                .withReason(String.format(
                        "Invalid argument %s with value %s", ex.getPropertyName(), ex.getValue()))
                .build());
    }
}