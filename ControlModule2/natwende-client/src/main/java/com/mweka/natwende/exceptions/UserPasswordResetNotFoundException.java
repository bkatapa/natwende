package com.mweka.natwende.exceptions;

public class UserPasswordResetNotFoundException extends EntityNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306419926982794365L;

	public UserPasswordResetNotFoundException(String message) {
		super(message);
	}
	
	public UserPasswordResetNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserPasswordResetNotFoundException(Exception exception) {
		this(exception.getMessage(), exception);
	}
}