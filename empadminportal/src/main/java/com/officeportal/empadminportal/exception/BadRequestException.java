package com.officeportal.empadminportal.exception;

public class BadRequestException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BadRequestException(String message) {
		super();
		this.message = message;
	}	
	
}
