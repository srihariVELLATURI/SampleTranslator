package com.sampleapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class ResourceAlreadyInUseException extends RuntimeException {

    private final String resourceName;
    private final transient Object fieldValue;
    private final String fieldName;
    
    											
    public ResourceAlreadyInUseException(String resourceName, Object fieldValue, String fieldName ) {
        super(String.format("%s '%s' already in use%s ", resourceName, fieldValue, fieldName));
      
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}