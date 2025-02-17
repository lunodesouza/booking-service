package com.github.lunodesouza.bookingservice.builder;

import com.github.lunodesouza.bookingservice.dto.request.BookingRequest;

import java.time.LocalDate;

public class BookingRequestTestBuilder {
    private Long propertyId = 1L;
    private String guestName = "Luno Souza";
    private LocalDate startDate = LocalDate.now().plusYears(1).plusDays(1);
    private LocalDate endDate = startDate.plusYears(1).plusDays(10);

    public BookingRequestTestBuilder withPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public BookingRequestTestBuilder withGuestName(String guestName) {
        this.guestName = guestName;
        return this;
    }

    public BookingRequestTestBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public BookingRequestTestBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public BookingRequest build() {
        return BookingRequest.builder()
                .propertyId(propertyId)
                .guestName(guestName)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
