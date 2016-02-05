package com.marcolenzo.gameboard.api;

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

import com.marcolenzo.gameboard.commons.model.FieldError;
import com.marcolenzo.gameboard.commons.model.ValidationError;



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
}
