package com.github.lunodesouza.bookingservice.controller;

import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;
import com.github.lunodesouza.bookingservice.dto.response.BookingResponse;
import com.github.lunodesouza.bookingservice.mapper.BookingMapper;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Operations for managing property bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    @Operation(summary = "Create booking", description = "Create a new property booking")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Date conflict with existing booking/block")
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request
    ) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingMapper.toResponse(booking));
    }

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieve list of all bookings")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingMapper.toResponseList(bookings));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieve a specific booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking found")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingMapper.toResponse(booking));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking", description = "Update an existing booking")
    @ApiResponse(responseCode = "200", description = "Booking updated successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    @ApiResponse(responseCode = "409", description = "Date conflict with existing booking/block")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest request
    ) {
        Booking updatedBooking = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(bookingMapper.toResponse(updatedBooking));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel booking", description = "Cancel an existing booking")
    @ApiResponse(responseCode = "200", description = "Booking cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    @ApiResponse(responseCode = "409", description = "Booking already cancelled")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        Booking canceledBooking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(bookingMapper.toResponse(canceledBooking));
    }

    @PostMapping("/{id}/rebook")
    @Operation(summary = "Rebook cancelled booking", description = "Reactivate a cancelled booking")
    @ApiResponse(responseCode = "200", description = "Booking reactivated successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    @ApiResponse(responseCode = "409", description = "Booking is not cancelled")
    public ResponseEntity<BookingResponse> rebookBooking(@PathVariable Long id) {
        Booking rebooked = bookingService.rebookBooking(id);
        return ResponseEntity.ok(bookingMapper.toResponse(rebooked));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking", description = "Permanently delete a booking")
    @ApiResponse(responseCode = "204", description = "Booking deleted successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}