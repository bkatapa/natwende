package com.mweka.natwende.exceptions;

public class EntityNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 2306419926982794365L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Exception exception) {
        this(exception.getMessage(), exception);
    }
}