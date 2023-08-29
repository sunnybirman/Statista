package com.statista.code.challenge.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;


// Exception handling logic for entire application
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private MessageSource messageSource;
	
	@Value("${error.response.contact}")
    private String contact;

	@Autowired
	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// Handles Validation failures 
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(BindException ex) {
		FieldErrorResponse errorResponse = new FieldErrorResponse();
		errorResponse.setContact(contact);
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(messageSource.getMessage("validation.error.message", null, LocaleContextHolder.getLocale()));
        errorResponse.setTimestamp(Instant.now());
		Map<String, String> field_errors = new HashMap<>();
		for (FieldError error : ex.getFieldErrors()) {
			String errorMessage = error.getDefaultMessage();
			logger.info("Validation failed- {}: {}",error.getField(), errorMessage);
			field_errors.put(error.getField(), errorMessage);
		}
		errorResponse.setField_errors(field_errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	// Handles invalid parameters  passed to GET requests 
	@ExceptionHandler({InvalidFormatException.class})
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setContact(contact);
		String invalidDepartmentMessage = messageSource.getMessage("invalid.department.value", new Object[]{ex.getValue()}, LocaleContextHolder.getLocale());
	    logger.error(invalidDepartmentMessage);
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(invalidDepartmentMessage);
        errorResponse.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	// Handles invalid parameters passed to GET requests 
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setContact(contact);
		String invalidDepartmentMessage = messageSource.getMessage("invalid.department.value", new Object[]{ex.getValue()}, LocaleContextHolder.getLocale());
	    logger.error(invalidDepartmentMessage);
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(invalidDepartmentMessage);
        errorResponse.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}


	//Handles scenario when requested resource is not available
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNoSuchElementFoundException(NoSuchElementException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setContact(contact);
		logger.error("No data found - {}",ex.getMessage());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	private ResponseEntity<ErrorResponse> handleInvalidValueException(String value) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setContact(contact);
		String invalidDepartmentMessage = messageSource.getMessage("invalid.department.value", new Object[]{value}, LocaleContextHolder.getLocale());
	    logger.error(invalidDepartmentMessage);
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(invalidDepartmentMessage);
        errorResponse.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

}
