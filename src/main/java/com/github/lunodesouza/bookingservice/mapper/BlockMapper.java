package com.github.lunodesouza.bookingservice.mapper;

import com.github.lunodesouza.bookingservice.dto.BlockRequest;
import com.github.lunodesouza.bookingservice.dto.BlockResponse;
import com.github.lunodesouza.bookingservice.model.Block;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockMapper {

    public Block toEntity(BlockRequest request) {
        return Block.builder()
                .propertyId(request.getPropertyId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .build();
    }

    public Block updateEntity(Block existing, BlockRequest request) {
        existing.setPropertyId(request.getPropertyId());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setReason(request.getReason());
        return existing;
    }

    public BlockResponse toResponse(Block block) {
        return BlockResponse.builder()
                .id(block.getId())
                .propertyId(block.getPropertyId())
                .startDate(block.getStartDate())
                .endDate(block.getEndDate())
                .reason(block.getReason())
                .build();
    }

    public List<BlockResponse> toResponseList(List<Block> blocks) {
        return blocks.stream()
                .map(this::toResponse)
                .toList();
    }
}
