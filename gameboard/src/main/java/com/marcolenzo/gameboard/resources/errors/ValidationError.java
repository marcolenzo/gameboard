package com.marcolenzo.gameboard.resources.errors;

import java.util.List;

import com.google.common.collect.Lists;

public class ValidationError {

	private String message;

	private List<FieldError> fieldErrors = Lists.newArrayList();

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fieldErrors
	 */
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * @param fieldErrors the fieldErrors to set
	 */
	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
