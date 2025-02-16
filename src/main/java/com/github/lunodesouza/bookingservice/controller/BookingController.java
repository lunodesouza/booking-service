package com.github.lunodesouza.bookingservice.controller;

import com.github.lunodesouza.bookingservice.dto.BookingRequest;
import com.github.lunodesouza.bookingservice.dto.BookingResponse;
import com.github.lunodesouza.bookingservice.mapper.BookingMapper;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request
    ) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingMapper.toResponse(booking));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingMapper.toResponseList(bookings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingMapper.toResponse(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest request
    ) {
        Booking updatedBooking = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(bookingMapper.toResponse(updatedBooking));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        Booking canceledBooking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(bookingMapper.toResponse(canceledBooking));
    }

    @PostMapping("/{id}/rebook")
    public ResponseEntity<BookingResponse> rebookBooking(@PathVariable Long id) {
        Booking rebooked = bookingService.rebookBooking(id);
        return ResponseEntity.ok(bookingMapper.toResponse(rebooked));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}