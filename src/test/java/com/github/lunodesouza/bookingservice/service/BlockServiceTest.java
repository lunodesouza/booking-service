package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.builder.BlockRequestTestBuilder;
import com.github.lunodesouza.bookingservice.builder.BlockTestBuilder;
import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;
import com.github.lunodesouza.bookingservice.exception.DateConflictException;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private BlockMapper blockMapper;

    @Mock
    private OverlapService overlapService;

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
                .validateConflicts(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);
    }

    @Test
    public void testCreateBlockWithConflict() {
        BlockRequest request = new BlockRequestTestBuilder().build();

        doThrow(new DateConflictException("Conflict with existing block"))
                .when(overlapService)
                .validateConflicts(request.getPropertyId(), request.getStartDate(), request.getEndDate(), null);

        DateConflictException exception = assertThrows(DateConflictException.class, () -> {
            blockService.createBlock(request);
        });

        assertEquals("Conflict with existing block", exception.getMessage());
    }

}
