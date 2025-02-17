package com.github.lunodesouza.bookingservice.builder;

import java.time.LocalDate;

public class OverlapTestParamsBuilder {
    private Long propertyId = 1L;
    private LocalDate startDate = LocalDate.now().plusYears(1).plusDays(1);
    private LocalDate endDate = startDate.plusYears(1).plusDays(10);
    private Long excludeId = null;

    public OverlapTestParamsBuilder withPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public OverlapTestParamsBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public OverlapTestParamsBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public OverlapTestParamsBuilder withExcludeId(Long excludeId) {
        this.excludeId = excludeId;
        return this;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getExcludeId() {
        return excludeId;
    }
}
