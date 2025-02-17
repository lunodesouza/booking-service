package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.builder.BlockRequestTestBuilder;
import com.github.lunodesouza.bookingservice.builder.BlockTestBuilder;
import com.github.lunodesouza.bookingservice.builder.BookingTestBuilder;
import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;
import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.model.Booking;
import com.github.lunodesouza.bookingservice.model.BookingStatus;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import com.github.lunodesouza.bookingservice.service.useCase.ValidateOverlapUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private BlockMapper blockMapper;

    @Mock
    private ValidateOverlapUseCase overlapService;

    @InjectMocks
    private BlockService blockService;

    @Test
    public void testCreateBlockSuccess() {
        BlockRequest request = new BlockRequestTestBuilder().build();
        Block block = new BlockTestBuilder().build();

        when(blockMapper.toEntity(request)).thenReturn(block);
        when(blockRepository.save(block)).thenReturn(block);

        Block result = blockService.createBlock(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(overlapService, times(1))
                .validate(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);
    }

    @Test
    public void testCreateBlockWithConflict() {
        BlockRequest request = new BlockRequestTestBuilder().build();

        doThrow(new DateConflictException("Conflict with existing block"))
                .when(overlapService)
                .validate(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);

        DateConflictException exception = assertThrows(DateConflictException.class, () -> {
            blockService.createBlock(request);
        });

        assertEquals("Conflict with existing block", exception.getMessage());
    }

    @Test
    public void testCreateBlock_ShouldThrowConflictWhenOverlapsWithConfirmedBooking() {
        BlockRequest blockRequest = new BlockRequestTestBuilder()
                .withPropertyId(1L)
                .withStartDate(LocalDate.of(2024, 1, 10))
                .withEndDate(LocalDate.of(2024, 1, 15))
                .build();

        Booking activeBooking = new BookingTestBuilder()
                .withPropertyId(1L)
                .withStartDate(LocalDate.of(2024, 1, 12))
                .withEndDate(LocalDate.of(2024, 1, 14))
                .withStatus(BookingStatus.ACTIVE)
                .build();

        doThrow(new DateConflictException("Conflict with confirmed booking ID: " + activeBooking.getId()))
                .when(overlapService)
                .validate(
                        eq(blockRequest.getPropertyId()),
                        eq(blockRequest.getStartDate()),
                        eq(blockRequest.getEndDate()),
                        isNull());

        DateConflictException exception = assertThrows(DateConflictException.class,
                () -> blockService.createBlock(blockRequest));

        assertThat(exception.getMessage())
                .contains("Conflict with confirmed booking ID: " + activeBooking.getId());

        verify(overlapService).validate(
                blockRequest.getPropertyId(),
                blockRequest.getStartDate(),
                blockRequest.getEndDate(),
                null
        );

        verify(blockRepository, never()).save(any());
        verify(blockMapper, never()).toEntity(any());
    }

}
