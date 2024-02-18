package com.cairone.rest.resource;

import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class MultiErrorResource {

    private final String message;
    private final String reason;
    private final Map<String, String> errors;

    @Builder(setterPrefix = "with")
    public MultiErrorResource(String message, String reason) {
        this.message = message;
        this.reason = reason;
        this.errors = new HashMap<>();
    }

    public MultiErrorResource addError(String field, String message) {
        errors.put(field, message);
        return this;
    }
}
