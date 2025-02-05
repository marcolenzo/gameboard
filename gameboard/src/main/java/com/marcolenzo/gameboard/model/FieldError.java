package com.marcolenzo.gameboard.model;

/**
 * Represents a field error in the gameboard application.
 * This class is used to store details of field validation errors.
 */
public class FieldError {

	private String field;

	private String error;

	/**
	 * Gets the field name associated with the error.
	 *
	 * @return the field name
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field name associated with the error.
	 *
	 * @param field the field name to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error message.
	 *
	 * @param error the error message to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
