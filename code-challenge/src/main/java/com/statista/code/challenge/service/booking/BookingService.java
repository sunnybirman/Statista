package com.statista.code.challenge.service.booking;

import java.util.Set;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

public interface BookingService {
	
	public long createBooking(Booking booking);
	public long updateBooking(Long bookingId, Booking booking);
	public Booking getBookingById(Long bookingId);
	public Set<Long> getBookingByDepartment(DepartmentType department);
	public Set<String> getAllCurrencies();
	public double getTotalPriceByCurrency(String currency);
	public String doBusinessForBooking(Long bookingId);

}
