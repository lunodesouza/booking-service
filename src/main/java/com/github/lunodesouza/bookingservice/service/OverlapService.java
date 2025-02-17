package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OverlapService {
    private final BookingRepository bookingRepository;
    private final BlockRepository blockRepository;

    public void validateConflicts(Long ropertyId, LocalDate startDate, LocalDate endDate, Long excludeId) {
        if (bookingRepository.existsConflictingBooking(
                ropertyId,
                startDate,
                endDate,
                excludeId)) {
            throw new DateConflictException("Conflict with existing booking");
        }
        if (blockRepository.existsConflictingBlock(
                ropertyId,
                startDate,
                endDate,
                excludeId))
            {
            throw new DateConflictException("Conflict with existing block");
        }

    }
}