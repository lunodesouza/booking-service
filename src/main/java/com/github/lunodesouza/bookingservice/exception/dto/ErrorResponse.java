package com.github.lunodesouza.bookingservice.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;
}
