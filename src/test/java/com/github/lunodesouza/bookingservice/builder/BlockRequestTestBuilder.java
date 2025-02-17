package com.github.lunodesouza.bookingservice.builder;

import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;

import java.time.LocalDate;

public class BlockRequestTestBuilder {
    private Long propertyId = 1L;
    private LocalDate startDate = LocalDate.of(2025, 5, 10);
    private LocalDate endDate = LocalDate.of(2025, 5, 15);
    private String reason = "Maintenance";

    public BlockRequestTestBuilder withPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public BlockRequestTestBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public BlockRequestTestBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public BlockRequestTestBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public BlockRequest build() {
        return BlockRequest.builder()
                .propertyId(propertyId)
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .build();
    }
}
