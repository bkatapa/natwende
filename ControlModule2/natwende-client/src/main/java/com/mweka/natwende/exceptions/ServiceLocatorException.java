package com.mweka.natwende.exceptions;

public class ServiceLocatorException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceLocatorException(String message) {
		super(message);
	}
	
	public ServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceLocatorException(Exception exception) {
		this(exception.getMessage(), exception);
	}
	
	
	
	
}
