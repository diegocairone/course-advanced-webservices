package com.cairone.rest.ctrl;

import com.cairone.error.AppClientException;
import com.cairone.core.exception.ResourceNotFoundException;
import com.cairone.error.AppServerException;
import com.cairone.rest.resource.MultiErrorResource;
import com.cairone.rest.resource.SingleErrorResource;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class AppAdviceCtrl {

    @ExceptionHandler(AppClientException.class)
    public ResponseEntity<SingleErrorResource> handleBadRequestExceptions(AppClientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleErrorResource.builder()
                .withMessage(ex.getMessage())
                .withReason("Invalid request")
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SingleErrorResource> handleValidationExceptions(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(SingleErrorResource.builder()
                .withMessage("Invalid arguments")
                .withReason(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SingleErrorResource> handleNotFoundExceptions(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleErrorResource.builder()
                .withMessage("Invalid arguments")
                .withReason(String.format(
                        "Invalid argument %s with value %s", ex.getPropertyName(), ex.getValue()))
                .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MultiErrorResource> handleNotFoundExceptions(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MultiErrorResource.builder()
                .withMessage(ex.getMessage())
                .withReason("Resource not found")
                .build()
                .addError("resource-name", ex.getResourceName())
                .addError("resource-id", ex.getResourceId())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MultiErrorResource> handleValidationExceptions(ConstraintViolationException ex) {

        MultiErrorResource multiErrorResource = MultiErrorResource.builder()
                .withMessage("At least one field in the request is invalid")
                .withReason("One or more fields are syntactically incorrect")
                .build();

        ex.getConstraintViolations().forEach(constraintViolation ->
                multiErrorResource.addError(
                        constraintViolation.getMessageTemplate(),
                        constraintViolation.getInvalidValue().toString()));

        return ResponseEntity.badRequest().body(multiErrorResource);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultiErrorResource> handleValidationExceptions(MethodArgumentNotValidException ex) {

        MultiErrorResource multiErrorResource = MultiErrorResource.builder()
                .withMessage("At least one field in the request is invalid")
                .withReason("One or more fields are syntactically incorrect")
                .build();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                multiErrorResource.addError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(multiErrorResource);
    }

    @ExceptionHandler(AppServerException.class)
    public ResponseEntity<SingleErrorResource> handleAppInternalServerError(AppServerException ex) {
        log.error("Internal server error: {}", ex.getTechnicalMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SingleErrorResource.builder()
                .withMessage(ex.getMessage())
                .withReason("Internal server error")
                .build());
    }
}