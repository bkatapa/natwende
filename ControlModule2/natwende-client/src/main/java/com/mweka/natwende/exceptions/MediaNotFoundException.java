package com.mweka.natwende.exceptions;

public class MediaNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306419926982794365L;

	public MediaNotFoundException(String message) {
		super(message);
	}
	
	public MediaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MediaNotFoundException(Exception exception) {
		this(exception.getMessage(), exception);
	}
	
	
	
	
}
