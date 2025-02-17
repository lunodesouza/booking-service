package com.github.lunodesouza.bookingservice.exception;

public class DateConflictException extends RuntimeException {
    public DateConflictException(String message) {
        super(message);
    }
}