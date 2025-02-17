package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.builder.OverlapTestParamsBuilder;
import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import com.github.lunodesouza.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OverlapServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private OverlapService overlapService;

    @Test
    public void testValidateConflicts_noConflicts() {
        OverlapTestParamsBuilder params = new OverlapTestParamsBuilder();

        when(bookingRepository.existsConflictingBooking(anyLong(), any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(false);
        when(blockRepository.existsConflictingBlock(anyLong(), any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(false);

        assertDoesNotThrow(() ->
                overlapService.validateConflicts(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId())
        );

        verify(bookingRepository).existsConflictingBooking(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId());
        verify(blockRepository).existsConflictingBlock(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId());
    }

    @Test
    public void testValidateConflicts_withBookingConflict() {
        OverlapTestParamsBuilder params = new OverlapTestParamsBuilder();

        when(bookingRepository.existsConflictingBooking(anyLong(), any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(true);

        DateConflictException exception = assertThrows(DateConflictException.class, () ->
                overlapService.validateConflicts(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId())
        );

        assertEquals("Conflict with existing booking", exception.getMessage());
        verify(bookingRepository).existsConflictingBooking(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId());

        verifyNoInteractions(blockRepository);
    }

    @Test
    public void testValidateConflicts_withBlockConflict() {
        OverlapTestParamsBuilder params = new OverlapTestParamsBuilder();

        when(bookingRepository.existsConflictingBooking(anyLong(), any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(false);
        when(blockRepository.existsConflictingBlock(anyLong(), any(LocalDate.class), any(LocalDate.class), any()))
                .thenReturn(true);

        DateConflictException exception = assertThrows(DateConflictException.class, () ->
                overlapService.validateConflicts(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId())
        );

        assertEquals("Conflict with existing block", exception.getMessage());
        verify(bookingRepository).existsConflictingBooking(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId());
        verify(blockRepository).existsConflictingBlock(params.getPropertyId(), params.getStartDate(), params.getEndDate(), params.getExcludeId());
    }
}
