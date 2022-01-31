package com.dheeraj.startup.server.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dheeraj.startup.entities.exception.BeanNotFoundException;
import com.dheeraj.startup.entities.exception.ExceptionResponse;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BeanNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(BeanNotFoundException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), HttpStatus.valueOf(ex.getStatusCode()).getReasonPhrase());
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(ex.getStatusCode()));
	}

}