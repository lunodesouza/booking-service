package com.github.lunodesouza.bookingservice.builder;

import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;

import java.time.LocalDate;

public class BookingTestBuilder {
    private Long id = 1L;
    private Long propertyId = 1L;
    private String guestName = "Luno Souza";
    private LocalDate startDate = LocalDate.of(2025, 5, 10);
    private LocalDate endDate = LocalDate.of(2025, 5, 15);
    private BookingStatus status = BookingStatus.ACTIVE;

    public BookingTestBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BookingTestBuilder withPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public BookingTestBuilder withGuestName(String guestName) {
        this.guestName = guestName;
        return this;
    }

    public BookingTestBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public BookingTestBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public BookingTestBuilder withStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public Booking build() {
        return Booking.builder()
                .id(id)
                .propertyId(propertyId)
                .guestName(guestName)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .build();
    }
}
