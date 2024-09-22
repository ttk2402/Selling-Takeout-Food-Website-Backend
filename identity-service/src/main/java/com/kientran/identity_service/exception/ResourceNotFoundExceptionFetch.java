package com.kientran.identity_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundExceptionFetch extends RuntimeException {

    private static final long serialVersionUID = 1L;
    String resourcename;
    String fieldname;
    String fieldvalue;

    public ResourceNotFoundExceptionFetch(String resourcename, String fieldname, String fieldvalue) {
        super(String.format("%s not found with %s: %s", resourcename, fieldname, fieldvalue));
        this.resourcename = resourcename;
        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
    }
}