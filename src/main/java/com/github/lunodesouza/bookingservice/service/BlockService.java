package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;
import com.github.lunodesouza.bookingservice.exception.BlockNotFoundException;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockService {
    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;
    private final OverlapService overlapService;

    public Block createBlock(BlockRequest request){
        log.info("createBlock request [{}]", request.toString());

        overlapService.validateConflicts(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                null);

        return blockRepository.save(blockMapper.toEntity(request));
    }

    public List<Block> getAllBlocks(){
        return blockRepository.findAll();
    }

    public Block getBlockById(Long id){
        log.info("getBlockById id [{}]", id);
        return blockRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("getBlockById: Block not found with id: {}", id);
                    return new BlockNotFoundException("Block not found with id: " + id);
                });
    }

    public Block updateBlock(Long id, BlockRequest request) {
        log.info("updateBlock with id [{}] request [{}]", id, request);

        overlapService.validateConflicts(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                id);

        Block existing = getBlockById(id);
        Block updated = blockMapper.updateEntity(existing, request);

        return blockRepository.save(updated);
    }

    public void deleteBlock(Long id) {
        log.info("deleteBlock with id [{}]", id);
        Block existing = getBlockById(id);
        blockRepository.delete(existing);
    }

}
