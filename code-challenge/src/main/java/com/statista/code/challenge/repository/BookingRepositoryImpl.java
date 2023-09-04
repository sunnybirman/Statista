package com.statista.code.challenge.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

	private static final Logger logger = LoggerFactory.getLogger(BookingRepositoryImpl.class);

	private Long nextBookingId = 1L;

	// Hashmap to store booking objects
	private Map<Long, Booking> bookingMap = new ConcurrentHashMap<>();

	// For quick retrieval using this as an index to fetch bookings from bookingMap
	private Map<DepartmentType, Set<Long>> departmentIndex = new ConcurrentHashMap<>();

	// For quick retrieval for Currency and SUM REST calls
	private Map<String, Double> currencyMap = new ConcurrentHashMap<>();

	@Override
	public synchronized long save(Booking booking) {

		try {
			nextBookingId+=1;
			// Begin custom transaction

			// Perform the operations within the transaction
			bookingMap.put(nextBookingId, booking);
			addToDepartmentIndex(nextBookingId, booking);
			addToCurrencyMap(booking);
			logger.info("Booking saved with ID: {}", nextBookingId);
			return nextBookingId;

		} catch (Exception e) {
			// Handle exception,perform rollback actions if needed
			logger.error("Error while saving booking", e);
			bookingMap.remove(nextBookingId);
			nextBookingId-=1;
			removeFromDepartmentIndex(nextBookingId, booking);
			removeFromCurrencyMap(booking);
			throw new RuntimeException("Error while saving booking", e);
		}
	}
	
	// Create new booking on update request if passed booking_Id in not present
	@Override
	public synchronized void save(long bookingId, Booking booking) {

		try {
			// Begin custom transaction
			// Perform the operations within the transaction
			bookingMap.put(bookingId, booking);
			addToDepartmentIndex(nextBookingId, booking);
			addToCurrencyMap(booking);
			logger.info("Booking saved with ID: {}", nextBookingId);

		} catch (Exception e) {
			// Handle exception,perform rollback actions if needed
			logger.error("Error while saving booking", e);
			bookingMap.remove(nextBookingId);
			removeFromDepartmentIndex(nextBookingId, booking);
			removeFromCurrencyMap(booking);
			throw new RuntimeException("Error while saving booking", e);
		}
	}

	@Override
	public Booking findById(Long bookingId) {
		return bookingMap.get(bookingId);
	}

	@Override
	public  Set<Long> findBookingByDepartment(DepartmentType department) {
	    logger.info("Finding bookings for department: {}", department);

	    Set<Long> bookingIds = departmentIndex.get(department);
	    
	    if(bookingIds==null)
	    	 logger.info("Found nothing");
		
	    return bookingIds;
	}


	@Override
	public List<Booking> findAll() {
		return new ArrayList<>(bookingMap.values());
	}

	@Override
	public synchronized void update(long bookingId, Booking booking) {
	    logger.info("Updating booking with ID: {}", bookingId);

	    // Get old booking
	    Booking oldBooking = bookingMap.get(bookingId);

	    // Begin custom transaction
	    try {
	        // Remove old booking
	        removeFromDepartmentIndex(bookingId, oldBooking);
	        removeFromCurrencyMap(oldBooking);

	        // Add the updated Booking
	        bookingMap.put(bookingId, booking);
	        addToDepartmentIndex(bookingId, booking);
	        addToCurrencyMap(booking);

	        logger.info("Booking with ID {} updated successfully", bookingId);
	    } catch (Exception e) {
	        // Rollback
	        addToDepartmentIndex(bookingId, booking);
	        addToCurrencyMap(booking);
	        logger.error("Error updating booking with ID: {}", bookingId, e);
	    }
	}


	@Override
	public void delete(Long bookingId) {
		Booking booking = bookingMap.get(bookingId);
		removeFromDepartmentIndex(nextBookingId, booking);
		removeFromCurrencyMap(booking);
		bookingMap.remove(bookingId);
	}

	@Override
	public Set<String> findAllCurrencies() {
		return currencyMap.keySet().stream().collect(Collectors.toSet());
	}

	@Override
	public Double getTotalPriceByCurrency(String currency) {
		return currencyMap.get(currency);
	}


	private void addToDepartmentIndex(long bookingId, Booking booking) {
		Set<Long> bookingIds = departmentIndex.get(booking.getDepartment());
		if(bookingIds==null)
			bookingIds = new HashSet<>();

		bookingIds.add(bookingId);
		departmentIndex.put(booking.getDepartment(), bookingIds);
	}

	// Remove booking from the department index
	private void removeFromDepartmentIndex(long bookingId, Booking booking) {
		Set<Long> bookingIds = departmentIndex.get(booking.getDepartment());
		bookingIds.remove(bookingId);
	}

	// Add booking price to the currency map
	private void addToCurrencyMap(Booking booking) {
		Double price = currencyMap.get(booking.getCurrency()) == null ? 0: currencyMap.get(booking.getCurrency());
		// Adding up the currency so that its readily available for SUM API call
		currencyMap.put(booking.getCurrency(),price+booking.getPrice());
	}

	// Remove booking price from the currency map
	private void removeFromCurrencyMap(Booking booking) {
		Double price = currencyMap.get(booking.getCurrency()) == null ? 0: currencyMap.get(booking.getCurrency());
		// Subtracting the currency for rollback changes
		currencyMap.put(booking.getCurrency(),price-booking.getPrice());
	}
}
