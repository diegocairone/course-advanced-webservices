package com.cairone.rest.resource;

import lombok.Builder;
import lombok.Value;

@Value
public class SingleErrorResource {

    private final String message;
    private final String reason;

    @Builder(setterPrefix = "with")
    public SingleErrorResource(String message, String reason) {
        this.message = message;
        this.reason = reason;
    }
}
