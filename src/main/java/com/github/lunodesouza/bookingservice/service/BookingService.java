package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.BookingRequest;
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

    public Booking createBooking(BookingRequest request) {
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
        Booking existing = getBookingById(id);
        Booking updated = bookingMapper.updateEntity(existing, request);
        return bookingRepository.save(updated);
    }

    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public Booking rebookBooking(Long id) {
        Booking booking = getBookingById(id);

        if (booking.getStatus() != BookingStatus.CANCELLED) {
            throw new InvalidOperationException("Only cancelled bookings can be rebooked");
        }

        booking.setStatus(BookingStatus.ACTIVE);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        getBookingById(id);

        bookingRepository.deleteById(id);
    }
}
