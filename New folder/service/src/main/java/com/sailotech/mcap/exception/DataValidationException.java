package com.sailotech.mcap.exception;

public class DataValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorResponse errorResponse;

	public DataValidationException(String errorCode, String errorMessage) {
		errorResponse = new ErrorResponse(errorCode, errorMessage);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

}
