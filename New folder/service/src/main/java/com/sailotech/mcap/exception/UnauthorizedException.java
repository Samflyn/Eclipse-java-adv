package com.sailotech.mcap.exception;

public class UnauthorizedException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorResponse errorResponse;

	public UnauthorizedException(String errorCode, String errorMessage) {
		errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorCode);
		errorResponse.setErrorMessage(errorMessage);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
}
