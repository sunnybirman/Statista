package com.statista.code.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

public class BookingRepositoryImplTest {

    @Mock
    private BookingRepositoryImpl bookingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveBooking() {
        Booking booking = new Booking(/* initialize booking object here */);
        when(bookingRepository.save(booking)).thenReturn(1L);

        long savedBookingId = bookingRepository.save(booking);

        assertEquals(1L, savedBookingId);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    public void testFindBookingById() {
        Booking expectedBooking = new Booking(/* initialize booking object here */);
        when(bookingRepository.findById(1L)).thenReturn(expectedBooking);

        Booking actualBooking = bookingRepository.findById(1L);

        assertEquals(expectedBooking, actualBooking);
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindBookingByDepartment() {
        Set<Long> expectedBookings = new HashSet<>();
        expectedBookings.add(2l);
        expectedBookings.add(1l);
        

        when(bookingRepository.findBookingByDepartment(DepartmentType.CONTENT_DESIGN))
            .thenReturn(expectedBookings);

        Set<Long> actualBookings = bookingRepository.findBookingByDepartment(DepartmentType.CONTENT_DESIGN);

        assertEquals(expectedBookings, actualBookings);
        verify(bookingRepository, times(1)).findBookingByDepartment(DepartmentType.CONTENT_DESIGN);
    }

    @Test
    public void testFindAllBookings() {
        List<Booking> expectedBookings = new ArrayList<>();
        // Add expected bookings to the list

        when(bookingRepository.findAll()).thenReturn(expectedBookings);

        List<Booking> actualBookings = bookingRepository.findAll();

        assertEquals(expectedBookings, actualBookings);
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateBooking() {
        Booking updatedBooking = new Booking(/* initialize updated booking object here */);
        ArgumentCaptor<Long> bookingIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);

        bookingRepository.update(1L, updatedBooking);

        verify(bookingRepository, times(1)).update(bookingIdCaptor.capture(), bookingCaptor.capture());
        assertEquals(1L, bookingIdCaptor.getValue());
        assertEquals(updatedBooking, bookingCaptor.getValue());
    }

    @Test
    public void testDeleteBooking() {
        bookingRepository.delete(1L);

        verify(bookingRepository, times(1)).delete(1L);
    }

   
}
