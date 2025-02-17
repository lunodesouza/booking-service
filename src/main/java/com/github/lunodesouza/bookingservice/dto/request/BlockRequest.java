package com.github.lunodesouza.bookingservice.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
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
public class BlockRequest {
    @NotNull(message = "propertyId must not be null")
    private Long propertyId;

    @NotNull(message = "startDate must not be null")
    @FutureOrPresent(message = "startDate must be a present or future date")
    private LocalDate startDate;

    @NotNull(message = "endDate must not be null")
    @FutureOrPresent(message = "endDate must be a present or future date")
    private LocalDate endDate;

    @Size(max = 255, message = "reason must be up to 255 characters")
    private String reason;

    @AssertTrue(message = "startDate must be before endDate")
    private boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !startDate.isAfter(endDate);
    }
}
