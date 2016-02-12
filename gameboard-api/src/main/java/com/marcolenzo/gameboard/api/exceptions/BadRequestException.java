package com.marcolenzo.gameboard.api.exceptions;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1924276987142356570L;

	public BadRequestException(String message) {
		super(message);
	}

}
