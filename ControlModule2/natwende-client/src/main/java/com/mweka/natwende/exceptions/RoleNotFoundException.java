package com.mweka.natwende.exceptions;

public class RoleNotFoundException extends EntityNotFoundException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundException(Exception exception) {
        this(exception.getMessage(), exception);
    }
}
