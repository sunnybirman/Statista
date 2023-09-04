package com.statista.code.challenge.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.statista.code.challenge.config.MessageConfig;
import com.statista.code.challenge.model.ErrorResponse;

public class GlobalExceptionHandlerTest {

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private MessageConfig messageConfig;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testHandleValidationException() {
		String contact = "testContact";
		BindException bindException = mock(BindException.class);
		List<FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new FieldError("objectName", "fieldName", "Error Message"));
		when(bindException.getFieldErrors()).thenReturn(fieldErrors);
		when(messageConfig.getContact()).thenReturn(contact);
		when(messageConfig.getValidationErrorMessage()).thenReturn("Mock validation message");

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationException(bindException);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(contact, response.getBody().getContact());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
		assertNotNull(response.getBody().getTimestamp());
		assertNotNull(response.getBody().getMessage());
	}

	@Test
	public void testHandleInvalidFormatException() {
		String contact = "testContact";
		InvalidFormatException invalidFormatException = mock(InvalidFormatException.class);
		when(messageConfig.getInValidDepartmentErrorMessage()).thenReturn("Invalid Department");
		when(messageConfig.getContact()).thenReturn(contact);
		when(invalidFormatException.getMessage()).thenReturn("Invalid format");

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidFormatException(invalidFormatException);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(contact, response.getBody().getContact());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
		assertNotNull(response.getBody().getTimestamp());
		assertEquals("Invalid Department", response.getBody().getMessage());
	}

	@Test
	public void testHandleMethodArgumentTypeMismatch() {
		String contact = "testContact";
		MethodArgumentTypeMismatchException mismatchException = mock(MethodArgumentTypeMismatchException.class);
		when(messageConfig.getInValidDepartmentErrorMessage()).thenReturn("Invalid Department");
		when(messageConfig.getContact()).thenReturn(contact);

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(mismatchException);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(contact, response.getBody().getContact());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
		assertNotNull(response.getBody().getTimestamp());
		assertEquals("Invalid Department", response.getBody().getMessage());
	}

	@Test
	public void testHandleNoSuchElementFoundException() {
		String contact = "testContact";
		NoSuchElementException noSuchElementException = new NoSuchElementException("No data found");
		when(messageConfig.getContact()).thenReturn(contact);

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNoSuchElementFoundException(noSuchElementException, null);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(contact, response.getBody().getContact());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
		assertNotNull(response.getBody().getTimestamp());
		assertEquals("No data found", response.getBody().getMessage());
	}
}
