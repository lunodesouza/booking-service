package com.github.lunodesouza.bookingservice.builder;

import com.github.lunodesouza.bookingservice.model.Block;

import java.time.LocalDate;

public class BlockTestBuilder {
    private Long id = 1L;
    private Long propertyId = 1L;
    private LocalDate startDate = LocalDate.now().plusYears(1).plusDays(1);
    private LocalDate endDate = startDate.plusYears(1).plusDays(10);
    private String reason = "Maintenance";

    public BlockTestBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BlockTestBuilder withPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public BlockTestBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public BlockTestBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public BlockTestBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public Block build() {
        return Block.builder()
                .id(id)
                .propertyId(propertyId)
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .build();
    }
}
