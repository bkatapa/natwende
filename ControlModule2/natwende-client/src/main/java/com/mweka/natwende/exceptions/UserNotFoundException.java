package com.mweka.natwende.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306419926982794365L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserNotFoundException(Exception exception) {
		this(exception.getMessage(), exception);
	}
}