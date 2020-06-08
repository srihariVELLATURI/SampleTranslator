package com.sampleapp.model;

public class ErrorResponse {
	private Object message;

	public ErrorResponse() {}

	public ErrorResponse(Object message) {
		this.message = message;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
}