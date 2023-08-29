package com.statista.code.challenge.service.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;
import com.statista.code.challenge.repository.BookingRepository;
import com.statista.code.challenge.service.department.Department;
import com.statista.code.challenge.service.notification.EmailNotification;
import com.statista.code.challenge.service.notification.NotificationService;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingManagement;

    @Mock
    private EmailNotification emailNotification;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Logger logger;

    @Mock
    private Department department;

    @Mock
    private Map<DepartmentType, Department> departmentMap;
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Department> businessLogics = Collections.singletonList(department);

        bookingService = new BookingServiceImpl(businessLogics, bookingManagement, emailNotification,
                notificationService);
    }

    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();
        when(bookingManagement.save(booking)).thenReturn(1L);

        long bookingId = bookingService.createBooking(booking);
        assertEquals(1L, bookingId);
    }

    @Test
    public void testUpdateBookingNotFound() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        when(bookingManagement.findById(bookingId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.updateBooking(bookingId, booking);
        });
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
        bookings.add(1l);
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
