package com.dheeraj.startup.entities.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;
	private String httpCodeMessage;
}