package com.marcolenzo.gameboard.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.FileUploadException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.exceptions.NotFoundException;
import com.marcolenzo.gameboard.model.FieldError;
import com.marcolenzo.gameboard.model.GenericError;
import com.marcolenzo.gameboard.model.ValidationError;

/**
 * Controller advice to handle exceptions globally across the application.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Handles MethodArgumentNotValidException and returns a ValidationError response.
	 *
	 * @param ex the MethodArgumentNotValidException
	 * @param locale the locale for error messages
	 * @return the ValidationError response
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationError processValidationError(MethodArgumentNotValidException ex, Locale locale) {
		BindingResult result = ex.getBindingResult();
		ValidationError validation = new ValidationError();
		validation.setMessage("Bad request. The request payload was not valid");
		for (org.springframework.validation.FieldError originalFieldError : result.getFieldErrors()) {
			FieldError fieldError = new FieldError();
			fieldError.setField(originalFieldError.getField());
			fieldError.setError(messageSource.getMessage(originalFieldError, locale));
			validation.getFieldErrors().add(fieldError);
		}
		return validation;
	}

	/**
	 * Handles BadRequestException and returns a GenericError response.
	 *
	 * @param ex the BadRequestException
	 * @param locale the locale for error messages
	 * @return the GenericError response
	 */
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public GenericError badRequest(BadRequestException ex, Locale locale) {
		return new GenericError(ex.getMessage());
	}

	/**
	 * Handles ForbiddenException and returns a GenericError response.
	 *
	 * @param ex the ForbiddenException
	 * @param locale the locale for error messages
	 * @return the GenericError response
	 */
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public GenericError forbidden(ForbiddenException ex, Locale locale) {
		return new GenericError(ex.getMessage());
	}

	/**
	 * Handles FileUploadException and returns a GenericError response.
	 *
	 * @param ex the FileUploadException
	 * @param locale the locale for error messages
	 * @return the GenericError response
	 */
	@ExceptionHandler(FileUploadException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public GenericError fileUpload(FileUploadException ex, Locale locale) {
		return new GenericError(ex.getMessage());
	}

	/**
	 * Handles NotFoundException and returns a GenericError response.
	 *
	 * @param ex the NotFoundException
	 * @param locale the locale for error messages
	 * @return the GenericError response
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public GenericError notFound(NotFoundException ex, Locale locale) {
		return new GenericError(ex.getMessage());
	}

}
