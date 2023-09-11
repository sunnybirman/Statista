package com.statista.code.challenge.controller;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.statista.code.challenge.config.MessageConfig;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.service.booking.BookingService;

@Controller
@RequestMapping("/bookingservice/v1")
@Validated
public class FooBarController implements FooBarApi {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private MessageConfig messageConfig;

	@Override
	public ResponseEntity<String> createBooking(Booking bookingRequest) {
		long id = bookingService.createBooking(bookingRequest);
		String message = String.format(messageConfig.getBookingConfirmationMessage(), id);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@Override
	public ResponseEntity<String> updateBooking(long bookingId, Booking bookingRequest) {
		bookingService.updateBooking(bookingId, bookingRequest);
		String message = messageConfig.getBookingUpdateMessage();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}


	@Override
	public ResponseEntity<Booking> getBookingById(long bookingId) {
		Booking booking = bookingService.getBookingById(bookingId);
		return ResponseEntity.status(HttpStatus.OK).body(booking);
	}

	@Override
	public ResponseEntity<Set<Long>> getBookingByDepartment(DepartmentType department) {
		Set<Long> bookings = bookingService.getBookingByDepartment(department);
		return ResponseEntity.status(HttpStatus.OK).body(bookings);
	}

	@Override
	public ResponseEntity<Set<String>> getCurrencies() {
		Set<String> curriencies	= bookingService.getAllCurrencies();
		return ResponseEntity.status(HttpStatus.OK).body(curriencies);
	}

	@Override
	public ResponseEntity<Double> getSumOfCurrency(String currency) {
		Double sum = bookingService.getTotalPriceByCurrency(currency);
		return ResponseEntity.status(HttpStatus.OK).body(sum);
	}

	@Override
	
	public ResponseEntity<String> getBusiness(long bookingId) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.doBusinessForBooking(bookingId));
	}
}