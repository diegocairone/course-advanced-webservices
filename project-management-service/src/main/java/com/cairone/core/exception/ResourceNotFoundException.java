package com.cairone.core.exception;

import lombok.Builder;

public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String resourceId;

    @Builder(setterPrefix = "with")
    public ResourceNotFoundException(String resourceName, String resourceId) {
        super(String.format("%s with ID %s not found", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }
}
