package com.github.lunodesouza.bookingservice.exception.handler;

import com.github.lunodesouza.bookingservice.exception.BlockNotFoundException;
import com.github.lunodesouza.bookingservice.exception.BookingNotFoundException;
import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("Validation failed", errors));
    }

    @ExceptionHandler({BookingNotFoundException.class, BlockNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Resource not found", ex.getMessage()));
    }

    @ExceptionHandler(DateConflictException.class)
    public ResponseEntity<ErrorResponse> handleDateConflict(DateConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Date Conflict", ex.getMessage()));
    }

}
