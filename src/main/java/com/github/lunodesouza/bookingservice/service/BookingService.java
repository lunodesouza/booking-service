package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;
import com.github.lunodesouza.bookingservice.exception.BookingNotFoundException;
import com.github.lunodesouza.bookingservice.exception.InvalidOperationException;
import com.github.lunodesouza.bookingservice.mapper.BookingMapper;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final OverlapService overlapService;

    public Booking createBooking(BookingRequest request) {

        overlapService.validateConflicts(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                null);

        return bookingRepository.save(bookingMapper.toEntity(request));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
    }

    public Booking updateBooking(Long id, BookingRequest request) {
        overlapService.validateConflicts(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                id);

        Booking existing = getBookingById(id);
        Booking updated = bookingMapper.updateEntity(existing, request);
        return bookingRepository.save(updated);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public Booking rebookBooking(Long id) {
        Booking booking = getBookingById(id);

        overlapService.validateConflicts(booking.getPropertyId(),
                booking.getStartDate(),
                booking.getEndDate(),
                null);

        if (booking.getStatus() != BookingStatus.CANCELLED) {
            throw new InvalidOperationException("Only cancelled bookings can be rebooked");
        }

        booking.setStatus(BookingStatus.ACTIVE);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        Booking existing = getBookingById(id);
        bookingRepository.delete(existing);
    }


}
