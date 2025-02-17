package com.github.lunodesouza.bookingservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            description = "Unique identifier of the property being blocked",
            example = "456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "propertyId must not be null")
    private Long propertyId;

    @Schema(
            description = "Block start date (must be today or in future)",
            example = "2025-06-01",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "startDate must not be null")
    @FutureOrPresent(message = "startDate must be a present or future date")
    private LocalDate startDate;

    @Schema(
            description = "Block end date (must be today or in future)",
            example = "2025-06-07",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "endDate must not be null")
    @FutureOrPresent(message = "endDate must be a present or future date")
    private LocalDate endDate;

    @Schema(
            description = "Reason for blocking the property",
            example = "Renovation work",
            maxLength = 255
    )
    @Size(max = 255, message = "reason must be up to 255 characters")
    private String reason;

    @Schema(
            description = "Internal date validation flag",
            hidden = true
    )
    @AssertTrue(message = "startDate must be before endDate")
    private boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !startDate.isAfter(endDate);
    }
}
