package com.statista.code.challenge.service.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.statista.code.challenge.config.MessageConfig;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.repository.BookingRepository;
import com.statista.code.challenge.service.department.Department;
import com.statista.code.challenge.service.notification.NotificationService;

public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingManagement;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Logger logger;

    @Mock
    private Department department;

    @Mock
    private Map<DepartmentType, Department> departmentMap;
    
    @Mock
    private MessageConfig config;

    private BookingServiceImpl bookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Department> businessLogics = Collections.singletonList(department);

        bookingService = new BookingServiceImpl(businessLogics, bookingManagement, notificationService, config);
    }

    
    @Test
    public void testCreateBookingSuccess() {
        Booking booking = new Booking(); // Create a Booking object
        long bookingId = 1L;

        // Mock the behavior of bookingManagement and notificationService
        when(bookingManagement.save(booking)).thenReturn(bookingId);
        when(config.getEmailNotificationSucess()).thenReturn("Success message");
        when(notificationService.sendNotification(anyString(), anyString())).thenReturn(true);

        long result = bookingService.createBooking(booking);

        verify(notificationService).sendNotification(booking.getEmail(), "Success message");
        assertNotNull(result);
    }

    @Test
    public void testCreateBookingFailure() {
        Booking booking = new Booking(); // Create a Booking object

        // Mock the behavior of bookingManagement and notificationService for failure
        when(bookingManagement.save(booking)).thenReturn(0L);
        when(config.getEmailNotificationFailure()).thenReturn("Failure message");
        when(notificationService.sendNotification(anyString(), anyString())).thenReturn(false);

        bookingService.createBooking(booking);

    }

    @Test
    public void testUpdateBookingNotFound() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        doNothing().when(bookingManagement).save(bookingId, booking);
        when(bookingManagement.findById(bookingId)).thenReturn((Booking) new HashMap<>().get("non_exixting_value"));
        when(config.getEmailNotificationSucess()).thenReturn("Sucessfull");

        assertEquals(1L, bookingService.updateBooking(bookingId, booking));
    }

    @Test
    public void testGetBookingById() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        when(bookingManagement.findById(bookingId)).thenReturn(booking);

        Booking resultBooking = bookingService.getBookingById(bookingId);

        assertEquals(booking, resultBooking);
    }

    @Test
    public void testGetBookingByIdNotFound() {
        Long bookingId = 1L;
        when(bookingManagement.findById(bookingId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.getBookingById(bookingId);
        });
    }

    @Test
    public void testGetBookingByDepartment() {
        DepartmentType departmentType = DepartmentType.CONTENT_DESIGN;
        Set<Long> bookings = new HashSet<>();
        bookings.add(1L);
        when(bookingManagement.findBookingByDepartment(departmentType)).thenReturn(bookings);

        Set<Long> resultBookings = bookingService.getBookingByDepartment(departmentType);

        assertEquals(bookings, resultBookings);
    }

    @Test
    public void testGetBookingByDepartmentNotFound() {
        DepartmentType departmentType = DepartmentType.CONTENT_DESIGN;
        when(bookingManagement.findBookingByDepartment(departmentType)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.getBookingByDepartment(departmentType);
        });
    }

    @Test
    public void testGetAllCurrencies() {
        Set<String> currencies = new HashSet<>(Arrays.asList("USD", "EUR"));
        when(bookingManagement.findAllCurrencies()).thenReturn(currencies);

        Set<String> resultCurrencies = bookingService.getAllCurrencies();

        assertEquals(currencies, resultCurrencies);
    }

    @Test
    public void testGetTotalPriceByCurrencyNotFound() {
        String currency = "USD";
        when(bookingManagement.getTotalPriceByCurrency(currency)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.getTotalPriceByCurrency(currency);
        });
    }
}
