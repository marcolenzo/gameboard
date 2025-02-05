package com.marcolenzo.gameboard.model;

/**
 * Represents a generic error in the gameboard application.
 * This class is used to store details of generic errors.
 */
public class GenericError {

	private String error;

	/**
	 * Constructs a new GenericError with the specified error message.
	 *
	 * @param error the error message
	 */
	public GenericError(String error) {
		this.error = error;
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
