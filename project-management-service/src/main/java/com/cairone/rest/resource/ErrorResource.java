package com.cairone.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource {

    private final String message;
    private final String reason;
    private final Map<String, String> errors;

    @Builder(setterPrefix = "with")
    public ErrorResource(String message, String reason) {
        this.message = message;
        this.reason = reason;
        this.errors = new HashMap<>();
    }

    public ErrorResource addError(String field, String message) {
        errors.put(field, message);
        return this;
    }
}
