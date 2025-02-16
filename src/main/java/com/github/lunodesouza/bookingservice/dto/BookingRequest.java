package com.github.lunodesouza.bookingservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
}
