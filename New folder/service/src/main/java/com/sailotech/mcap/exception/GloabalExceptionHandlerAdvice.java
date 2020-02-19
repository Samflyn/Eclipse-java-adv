package com.sailotech.mcap.exception;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloabalExceptionHandlerAdvice {

	@Autowired
	private ObjectFactory<ErrorResponse> gstnErrResponseFactory;

	@ExceptionHandler(DataValidationException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(DataValidationException ex) {
		return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = gstnErrResponseFactory.getObject();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(NullPointerException ex) {
		ErrorResponse error = gstnErrResponseFactory.getObject();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MesserApAutomationException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(MesserApAutomationException ex) {
		return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(UnauthorizedException ex) {
		return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(BadCredentialsException ex) {
		ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				"Invalid Username or Password");
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

}