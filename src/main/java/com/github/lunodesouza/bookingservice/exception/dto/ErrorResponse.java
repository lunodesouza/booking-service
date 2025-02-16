package com.github.lunodesouza.bookingservice.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private List<String> details;

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = List.of(details);
    }
}
