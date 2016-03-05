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
import com.marcolenzo.gameboard.resources.errors.FieldError;
import com.marcolenzo.gameboard.resources.errors.ValidationError;



@ControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	private MessageSource messageSource;

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

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String badRequest(BadRequestException ex, Locale locale) {
		return ex.getMessage();
	}


	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public String forbidden(ForbiddenException ex, Locale locale) {
		return ex.getMessage();
	}

	@ExceptionHandler(FileUploadException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String fileUpload(FileUploadException ex, Locale locale) {
		return ex.getMessage();
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String notFound(NotFoundException ex, Locale locale) {
		return ex.getMessage();
	}

}
