package com.statista.code.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.statista.code.challenge.config.MessageConfig;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.service.booking.BookingService;

public class FooBarControllerTest {

	@InjectMocks
	private FooBarController fooBarController;

	@Mock
	private BookingService bookingService;

	@Mock
	private MessageConfig messageConfig;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateBooking() {
		Booking bookingRequest = new Booking(); // Create a valid Booking object here
		when(bookingService.createBooking(bookingRequest)).thenReturn(1L);
		when(messageConfig.getBookingConfirmationMessage()).thenReturn("Booking created with ID: %d");

		ResponseEntity<String> response = fooBarController.createBooking(bookingRequest);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Booking created with ID: 1", response.getBody());
	}


	@Test
	public void testGetBookingById() {
		long bookingId = 1L;
		Booking expectedBooking = new Booking(); // Create an expected Booking object here
		when(bookingService.getBookingById(bookingId)).thenReturn(expectedBooking);

		ResponseEntity<Booking> response = fooBarController.getBookingById(bookingId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedBooking, response.getBody());
	}

	@Test
	public void testGetBookingByDepartment() {
		DepartmentType department = DepartmentType.CUSTOMER_SUPPORT; 
		Set<Long> expectedBookings = new HashSet<>();
		when(bookingService.getBookingByDepartment(department)).thenReturn(expectedBookings);

		ResponseEntity<Set<Long>> response = fooBarController.getBookingByDepartment(department);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedBookings, response.getBody());
	}

	@Test
	public void testGetCurrencies() {
		Set<String> expectedCurrencies = new HashSet<>(); 
		when(bookingService.getAllCurrencies()).thenReturn(expectedCurrencies);

		ResponseEntity<Set<String>> response = fooBarController.getCurrencies();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedCurrencies, response.getBody());
	}

	@Test
	public void testGetSumOfCurrency() {
		String currency = "USD"; 
		Double expectedSum = 1000.0; 

		when(bookingService.getTotalPriceByCurrency(currency)).thenReturn(expectedSum);

		ResponseEntity<Double> response = fooBarController.getSumOfCurrency(currency);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedSum, response.getBody());
	}

	@Test
	public void testGetBusiness() {
        long bookingId = 1L;
        String expectedBusinessResult = "Some business result"; 

        when(bookingService.doBusinessForBooking(bookingId)).thenReturn(expectedBusinessResult);

        ResponseEntity<String> response = fooBarController.getBusiness(bookingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBusinessResult, response.getBody());
    }
}