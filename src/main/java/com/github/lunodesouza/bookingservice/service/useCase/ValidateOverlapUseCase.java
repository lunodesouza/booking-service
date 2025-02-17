package com.github.lunodesouza.bookingservice.service.useCase;

import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateOverlapUseCase {
    private final BookingRepository bookingRepository;
    private final BlockRepository blockRepository;

    public void validate(Long propertyId, LocalDate startDate, LocalDate endDate, Long excludeId) {
        boolean hasBookingConflict = bookingRepository.existsConflictingBooking(propertyId, startDate, endDate, excludeId);
        boolean hasBlockConflict = blockRepository.existsConflictingBlock(propertyId, startDate, endDate, excludeId);

        if (hasBookingConflict) {
            log.error("Conflict with existing booking propertyId: [{}] startDate: [{}]: endDate: [{}] excludeId: [{}]",
                    propertyId,
                    startDate,
                    endDate,
                    excludeId);

            throw new DateConflictException("Conflict with existing booking");
        }

        if (hasBlockConflict){
            log.error("Conflict with existing block propertyId: [{}] startDate: [{}]: endDate: [{}] excludeId: [{}]",
                        propertyId,
                        startDate,
                        endDate,
                        excludeId);

            throw new DateConflictException("Conflict with existing block");
        }
    }
}