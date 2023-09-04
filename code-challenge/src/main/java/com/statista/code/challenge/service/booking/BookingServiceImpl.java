package com.statista.code.challenge.service.booking;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statista.code.challenge.config.MessageConfig;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.repository.BookingRepository;
import com.statista.code.challenge.service.department.Department;
import com.statista.code.challenge.service.notification.NotificationService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	private MessageConfig messageConfig;

	private final Map<DepartmentType, Department> departmentMap;

	private BookingRepository bookingManagment;

	private NotificationService notificationService;

	@Autowired
	public BookingServiceImpl(List<Department> businessLogics, BookingRepository bookingManagment,
			NotificationService notificationService, MessageConfig messageConfig) {
		this.departmentMap = businessLogics.stream()
				.collect(Collectors.toMap(Department::getDepartment, Function.identity()));
		this.bookingManagment = bookingManagment;
		this.notificationService = notificationService;
		this.messageConfig = messageConfig;
	}

	public long createBooking(Booking booking) {
		logger.info("Creating booking: {}", booking);
		long bookingId = bookingManagment.save(booking);
		logger.info("Booking created with ID: {}", bookingId);
		String message = null;
		if(bookingId != 0)
			message = String.format(messageConfig.getEmailNotificationSucess(), booking);
		else
			message = messageConfig.getEmailNotificationFailure();

		boolean isEmailSent = notificationService.sendNotification(booking.getEmail(), message);
		if(!isEmailSent)
			logger.error("Email service encountered issue for booking -  {}", bookingId);
		return bookingId;
	}

	public long createBooking(long bookingId, Booking booking) {
		logger.info("Creating booking: {}", booking);
		bookingManagment.save(bookingId, booking);
		logger.info("Booking created with ID: {}", bookingId);
		String message = String.format(messageConfig.getEmailNotificationSucess(), booking);
		boolean isEmailSent = notificationService.sendNotification(booking.getEmail(), message);
		if(!isEmailSent)
			logger.error("Email service encountered issue for booking -  {}", bookingId);
		return bookingId;
	}

	public long updateBooking(Long bookingId, Booking booking) {
		logger.info("Updating booking with ID: {}", bookingId);
		// Check if the booking exists
		Optional<Booking> existingBooking = Optional.ofNullable(bookingManagment.findById(bookingId));
		if (existingBooking.isPresent()) {
			bookingManagment.update(bookingId, booking);
			logger.info("Booking updated: {}", booking);
		} else {
			logger.error("Booking not found with ID: {}, creating new Booking", bookingId);
			createBooking(bookingId,booking);
		}
		return bookingId;
	}

	public Booking getBookingById(Long bookingId) {
		logger.info("Retrieving booking by ID: {}", bookingId);
		return Optional.ofNullable(bookingManagment.findById(bookingId))
				.orElseThrow(() -> {
					logger.error("Booking not found with ID: {}", bookingId);
					return new NoSuchElementException("Booking not found with id: " + bookingId);
				});
	}

	public Set<Long> getBookingByDepartment(DepartmentType department) {
		return Optional.ofNullable(bookingManagment.findBookingByDepartment(department))
				.orElseThrow(() -> new NoSuchElementException("Department not found with name: " + department));
	}

	public Set<String> getAllCurrencies() {
		logger.info("Getting all currency");
		return bookingManagment.findAllCurrencies();
	}

	public double getTotalPriceByCurrency(String currency) {
		logger.info("Getting total price for currency: {}", currency);
		return Optional.ofNullable(bookingManagment.getTotalPriceByCurrency(currency))
				.orElseThrow(() -> {
					logger.error("Currency not found: {}", currency);
					return new NoSuchElementException("Currency not found with symbol: " + currency);
				});
	}


	public String doBusinessForBooking(Long bookingId) { 
		Booking booking =Optional.ofNullable(bookingManagment.findById(bookingId))
				.orElseThrow(() -> new
						NoSuchElementException("Booking not found with id: " + bookingId));

		Department department = departmentMap.get(booking.getDepartment());
		return department.doBusiness();
	}
}
