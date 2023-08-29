package com.statista.code.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.service.booking.BookingService;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(FooBarController.class)
public class FooBarControllerTest {

	@Mock
    private BookingService bookingService;

    @InjectMocks
    private FooBarController fooBarController;
    private Booking mockBooking;
    
    @Before
    public void setup() {
    	mockBooking  = new Booking("test", 22.2, "USD", 68312484, "test@em.com", DepartmentType.CUSTOMER_SUPPORT);
    }
    
    @Test
    public void testCreateBooking() {
        when(bookingService.createBooking(any(Booking.class))).thenReturn(1L);
        ResponseEntity<String> response = fooBarController.createBooking(mockBooking);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Long.valueOf(1), response.getBody());
    }
    
    @Test
    public void testUpdateBooking() {
        when(bookingService.updateBooking(anyLong(), any(Booking.class))).thenReturn(1L);

        ResponseEntity<String> response = fooBarController.updateBooking(1L, mockBooking);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Long.valueOf(1), response.getBody());
    }
    
    @Test
    public void testGetBookingById() {
        when(bookingService.getBookingById(1L)).thenReturn(mockBooking);

        ResponseEntity<Booking> response = fooBarController.getBookingById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBooking, response.getBody());
    }

    @Test
    public void testGetBookingByDepartment() {
    	Set<Long> mockBookingIds = new HashSet<>();// Create mock Booking list
    	mockBookingIds.add(1l);
    	mockBookingIds.add(2l);
        when(bookingService.getBookingByDepartment(any(DepartmentType.class))).thenReturn(mockBookingIds);

        ResponseEntity<Set<Long>> response = fooBarController.getBookingByDepartment(DepartmentType.CUSTOMER_SUPPORT);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBookingIds, response.getBody());
    }

    @Test
    public void testGetCurrencies() {
        Set<String> mockCurrencies = new HashSet<>(Arrays.asList("USD", "EUR"));
        when(bookingService.getAllCurrencies()).thenReturn(mockCurrencies);

        ResponseEntity<Set<String>> response = fooBarController.getCurrencies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCurrencies, response.getBody());
    }

    @Test
    public void testGetSumOfCurrency() {
        Double mockSum = 1000.0; // Mock sum value
        when(bookingService.getTotalPriceByCurrency("USD")).thenReturn(mockSum);

        ResponseEntity<Double> response = fooBarController.getSumOfCurrency("USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSum, response.getBody());
    }

    @Test
    public void testGetBusiness() {
        String mockBusinessResult = "Business done!"; // Mock business result
        when(bookingService.doBusinessForBooking(1L)).thenReturn(mockBusinessResult);

        ResponseEntity<String> response = fooBarController.getBusiness(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBusinessResult, response.getBody());
    }
    
    @Test
    public void testCreateBooking_InvalidInput() {
        // Simulate invalid input
        Booking invalidBooking = new Booking();
        invalidBooking.setEmail("RRR");

        ResponseEntity<String> response = fooBarController.createBooking(invalidBooking);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    
}
