package com.dheeraj.startup.entities.exception;

import org.springframework.http.HttpStatus;

public class BeanNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private int statusCode;

	public BeanNotFoundException(String message, HttpStatus status) {
		super(message);
		this.statusCode = status.value();
	}

	public int getStatusCode() {
		return statusCode;
	}

}
