package com.github.lunodesouza.bookingservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long propertyId;
    private String guestName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
