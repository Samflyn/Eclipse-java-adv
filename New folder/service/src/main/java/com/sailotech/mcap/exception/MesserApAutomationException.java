package com.sailotech.mcap.exception;

public class MesserApAutomationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorResponse errorResponse;

	public MesserApAutomationException(String errorCode, String errorMessage) {
		errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorCode);
		errorResponse.setErrorMessage(errorMessage);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

}
