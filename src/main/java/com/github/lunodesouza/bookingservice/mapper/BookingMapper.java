package com.github.lunodesouza.bookingservice.mapper;

import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;
import com.github.lunodesouza.bookingservice.dto.response.BookingResponse;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public Booking toEntity(BookingRequest request) {
        return Booking.builder()
                .propertyId(request.getPropertyId())
                .guestName(request.getGuestName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(BookingStatus.ACTIVE)
                .build();
    }

    public Booking updateEntity(Booking existing, BookingRequest request) {
        existing.setPropertyId(request.getPropertyId());
        existing.setGuestName(request.getGuestName());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        return existing;
    }

    public List<BookingResponse> toResponseList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toResponse)
                .toList();
    }

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .propertyId(booking.getPropertyId())
                .guestName(booking.getGuestName())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(booking.getStatus().name())
                .build();
    }
}
