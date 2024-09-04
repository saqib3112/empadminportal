package com.officeportal.empadminportal.response;

public class ApiResponseMessage {

	private String message;

	public ApiResponseMessage(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
