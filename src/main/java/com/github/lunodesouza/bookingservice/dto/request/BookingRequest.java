package com.github.lunodesouza.bookingservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            description = "ID of the property being booked",
            example = "123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "propertyId is required")
    private Long propertyId;

    @Schema(
            description = "Full name of the guest making the booking",
            example = "Luno Souza",
            maxLength = 255,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "guestName cannot be empty")
    @Size(max = 255, message = "guestName must be up to 255 characters")
    private String guestName;

    @Schema(
            description = "Booking start date (must be today or in future)",
            example = "2027-12-01",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "startDate is required")
    @FutureOrPresent(message = "startDate must be a present or future date")
    private LocalDate startDate;

    @Schema(
            description = "Booking end date (must be today or in future)",
            example = "2028-12-07",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate must be a present or future date")
    private LocalDate endDate;

    @Schema(
            description = "Internal validation flag",
            hidden = true
    )
    @AssertTrue(message = "startDate must be before endDate")
    private boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !startDate.isAfter(endDate);
    }
}
