package com.sailotech.mcap.exception;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Nagendra
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "errorCode", "errorMessage", })
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("errorCode")
	private String errorCode;

	@JsonProperty("errorMessage")
	private String errorMessage;

	public ErrorResponse() {

	}

	public ErrorResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
