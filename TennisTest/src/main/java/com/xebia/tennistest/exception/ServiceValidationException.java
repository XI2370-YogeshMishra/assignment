package com.xebia.tennistest.exception;

import java.util.Collection;

public class ServiceValidationException extends RuntimeException {

    private final Collection<String> errors;

    public ServiceValidationException(Collection<String> errors) {
        this.errors = errors;
    }

    public Collection<String> getErrors() {
        return errors;
    }
}
