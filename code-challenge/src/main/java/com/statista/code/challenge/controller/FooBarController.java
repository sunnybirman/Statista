package com.statista.code.challenge.controller;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.service.booking.BookingService;

@Controller
@RequestMapping("/bookingservice")
@Validated
public class FooBarController {

	@Autowired
	private BookingService bookingService;

	// Create a new booking
	@PostMapping("/booking")
	public ResponseEntity<Long> createBooking(@RequestBody @Valid Booking bookingRequest) {
		long id = bookingService.createBooking(bookingRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}

	// Update an existing booking by booking ID
	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<Long> updateBooking(@PathVariable long bookingId, @RequestBody @Valid Booking bookingRequest) {
		long id = bookingService.updateBooking(bookingId, bookingRequest);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	

	// Retrieve a booking by its ID
	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> getBookingById(@PathVariable long bookingId) {
		Booking booking = bookingService.getBookingById(bookingId);
		return ResponseEntity.status(HttpStatus.OK).body(booking);
	}

	// Retrieve bookings by department
	@GetMapping("bookings/department/{department}")
	public ResponseEntity<List<Booking>> getBookingByDepartment(@PathVariable DepartmentType department) {
		List<Booking> bookings = bookingService.getBookingByDepartment(department);
		return ResponseEntity.status(HttpStatus.OK).body(bookings);
	}
	
	// Retrieve a list of available currencies
	@GetMapping("bookings/currencies")
	public ResponseEntity<Set<String>> getCurrencies() {
		Set<String> curriencies	= bookingService.getAllCurrencies();
		return ResponseEntity.status(HttpStatus.OK).body(curriencies);
	}
	
	// Calculate the total sum of bookings in a specific currency
	@GetMapping("sum/{currency}")
	public ResponseEntity<Double> getSumOfCurrency(@PathVariable String currency) {
		Double sum = bookingService.getTotalPriceByCurrency(currency);
		return ResponseEntity.status(HttpStatus.OK).body(sum);
	}
	
	// Perform business operations for a specific booking
	@GetMapping("bookings/dobusiness/{bookingId}")
	public ResponseEntity<String> getBusiness(@PathVariable long bookingId) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.doBusinessForBooking(bookingId));
	}
}