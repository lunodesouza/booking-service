package com.github.lunodesouza.bookingservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotNull(message = "propertyId is required")
    private Long propertyId;

    @NotBlank(message = "guestName cannot be empty")
    @Size(max = 255, message = "guestName must be up to 255 characters")
    private String guestName;

    @NotNull(message = "startDate is required")
    @FutureOrPresent(message = "startDate must be a present or future date")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate must be a present or future date")
    private LocalDate endDate;

    @AssertTrue(message = "startDate must be before endDate")
    private boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !startDate.isAfter(endDate);
    }
}
