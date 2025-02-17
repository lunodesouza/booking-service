package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;
import com.github.lunodesouza.bookingservice.exception.BookingNotFoundException;
import com.github.lunodesouza.bookingservice.exception.InvalidOperationException;
import com.github.lunodesouza.bookingservice.mapper.BookingMapper;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import com.github.lunodesouza.bookingservice.service.useCase.ValidateOverlapUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ValidateOverlapUseCase validateOverlapUseCase;

    @Transactional
    public Booking createBooking(BookingRequest request) {
        log.info("createBlock request[{}]", request.toString());

        validateOverlapUseCase.validate(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                null);

        return bookingRepository.save(bookingMapper.toEntity(request));
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        log.info("getBookingById [{}]", id);
        return bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Booking not found with id [{}]", id);
                    return new BookingNotFoundException("Booking not found with id: " + id);
                });
    }

    @Transactional
    public Booking updateBooking(Long id, BookingRequest request) {
        log.info("updateBooking with id [{}] request [{}]", id, request);

        validateOverlapUseCase.validate(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                id);

        Booking existing = getBookingById(id);
        Booking updated = bookingMapper.updateEntity(existing, request);
        return bookingRepository.save(updated);
    }

    @Transactional
    public Booking cancelBooking(Long id) {
        log.info("cancelBooking with id [{}]", id);

        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking rebookBooking(Long id) {
        log.info("rebookBooking with id [{}]", id);
        Booking booking = getBookingById(id);

        validateOverlapUseCase.validate(booking.getPropertyId(),
                booking.getStartDate(),
                booking.getEndDate(),
                null);

        if (booking.getStatus() != BookingStatus.CANCELLED) {
            log.error("Status {} - Only cancelled bookings can be rebooked Booking [{}]", booking.getStatus(), booking);
            throw new InvalidOperationException("Only cancelled bookings can be rebooked");
        }

        booking.setStatus(BookingStatus.ACTIVE);
        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        log.info("deleteBooking id [{}]", id);
        Booking existing = getBookingById(id);
        bookingRepository.delete(existing);
    }


}
