package com.statista.code.challenge.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FooBarApi {

	// Create a new booking
	@PostMapping(value ="/booking", produces = "application/json")
	@ApiOperation(value = "Create a new booking")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Booking created"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied")
    })
	ResponseEntity<String> createBooking(@RequestBody @Valid Booking bookingRequest);

	
	// Update an existing booking by booking ID
	@PutMapping(value ="/booking/{bookingId}", produces = "application/json")
	@ApiOperation(value = "Update existing booking")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Booking updated"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
        @ApiResponse(code = 404, message = "Booking not found")
    })
	ResponseEntity<String> updateBooking(@PathVariable long bookingId, @RequestBody @Valid Booking bookingRequest);

	
	// Retrieve a booking by its ID
	@GetMapping(value ="/booking/{bookingId}", produces = "application/json")
	@ApiOperation(value = "Get booking with given ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Booking retrieved"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
        @ApiResponse(code = 404, message = "Booking not found")
    })
	ResponseEntity<Booking> getBookingById(@PathVariable long bookingId);
	
	
	// Retrieve bookings by department
	@GetMapping(value ="bookings/department/{department}", produces = "application/json")
	@ApiOperation(value = "Get booking with given Department")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Bookings retrieved"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
        @ApiResponse(code = 404, message = "Department not found")
    })
	ResponseEntity<Set<Long>> getBookingByDepartment(@PathVariable DepartmentType department);
	
	

	// Retrieve a list of available currencies
	@GetMapping(value ="bookings/currencies", produces = "application/json")
	@ApiOperation(value = "Get all currencies")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Curriencies retrieved"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
    })
	ResponseEntity<Set<String>> getCurrencies();

	
	// Calculate the total sum of bookings in a specific currency
	@GetMapping(value ="sum/{currency}", produces = "application/json")
	@ApiOperation(value = "Get sum of currencies")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Sum retrieved"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
        @ApiResponse(code = 404, message = "Currency not found")
    })
	ResponseEntity<Double> getSumOfCurrency(@PathVariable String currency);

	
	// Perform business operations for a specific booking
	@GetMapping(value ="bookings/dobusiness/{bookingId}", produces = "application/json")
	@ApiOperation(value = "Do business with the Department")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Get Business"),
        @ApiResponse(code = 400, message = "Bad request. Invalid input data"),
        @ApiResponse(code = 403, message = "Forbidden. Access denied"),
        @ApiResponse(code = 404, message = "Booking not found")
    })
	ResponseEntity<String> getBusiness(@PathVariable long bookingId);

}