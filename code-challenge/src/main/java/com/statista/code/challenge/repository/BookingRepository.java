package com.statista.code.challenge.repository;

import java.util.List;
import java.util.Set;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

public interface BookingRepository {

	long save(Booking booking);
	
	void save(long bookingId, Booking booking);

	Booking findById(Long bookingId);

	Set<Long> findBookingByDepartment(DepartmentType department);

	List<Booking> findAll();

	void update(long bookingId, Booking booking);

	void delete(Long bookingId);

	Set<String> findAllCurrencies();

	Double getTotalPriceByCurrency(String currency);

}