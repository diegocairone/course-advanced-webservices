package com.cairone.core.exception;

import com.cairone.error.AppClientException;
import lombok.Builder;

public class ResourceNotFoundException extends AppClientException {

    private final String resourceName;
    private final String resourceId;

    @Builder(setterPrefix = "with")
    public ResourceNotFoundException(String resourceName, String resourceId) {
        super("%s with ID %s not found", resourceName, resourceId);
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
