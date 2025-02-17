package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.builder.BookingRequestTestBuilder;
import com.github.lunodesouza.bookingservice.builder.BookingTestBuilder;
import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;
import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.exception.InvalidOperationException;
import com.github.lunodesouza.bookingservice.mapper.BookingMapper;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private OverlapService overlapService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void testCreateBookingSuccess() {
        BookingRequest request = new BookingRequestTestBuilder().build();
        Booking bookingEntity = new BookingTestBuilder().build();

        when(bookingMapper.toEntity(request)).thenReturn(bookingEntity);
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);

        Booking result = bookingService.createBooking(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(overlapService, times(1))
                .validateConflicts(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);
    }

    @Test
    public void testCreateBookingConflict() {
        BookingRequest request = new BookingRequestTestBuilder().build();

        doThrow(new DateConflictException("Conflict with existing booking"))
                .when(overlapService)
                .validateConflicts(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);

        DateConflictException exception = assertThrows(DateConflictException.class, () -> {
            bookingService.createBooking(request);
        });

        assertEquals("Conflict with existing booking", exception.getMessage());
    }

    @Test
    public void testRebookBookingInvalidOperation() {
        Booking booking = new BookingTestBuilder()
                .withStatus(BookingStatus.ACTIVE)
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            bookingService.rebookBooking(1L);
        });

        assertEquals("Only cancelled bookings can be rebooked", exception.getMessage());
    }

    @Test
    public void testRebookBookingSuccess() {
        Booking booking = new BookingTestBuilder()
                .withStatus(BookingStatus.CANCELLED)
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        doNothing().when(overlapService)
                .validateConflicts(booking.getPropertyId(), booking.getStartDate(), booking.getEndDate(), null);

        Booking updatedBooking = new BookingTestBuilder()
                .withStatus(BookingStatus.ACTIVE)
                .build();

        when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);

        Booking result = bookingService.rebookBooking(1L);

        assertNotNull(result);
        assertEquals(BookingStatus.ACTIVE, result.getStatus());
        verify(overlapService, times(1))
                .validateConflicts(booking.getPropertyId(), booking.getStartDate(), booking.getEndDate(), null);
    }

    @Test
    public void testCancelBooking() {
        Booking booking = new BookingTestBuilder()
                .withStatus(BookingStatus.ACTIVE)
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking cancelled = bookingService.cancelBooking(1L);

        assertNotNull(cancelled);
        assertEquals(BookingStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    public void testRebookBookingWithBlockConflict() {
        Booking booking = new BookingTestBuilder()
                .withStatus(BookingStatus.CANCELLED)
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        doThrow(new DateConflictException("Conflict with existing block"))
                .when(overlapService)
                .validateConflicts(booking.getPropertyId(), booking.getStartDate(), booking.getEndDate(), null);

        DateConflictException exception = assertThrows(DateConflictException.class, () -> {
            bookingService.rebookBooking(1L);
        });

        assertEquals("Conflict with existing block", exception.getMessage());
    }
}
