package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;
import com.github.lunodesouza.bookingservice.exception.BlockNotFoundException;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import com.github.lunodesouza.bookingservice.service.useCase.ValidateOverlapUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockService {
    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;
    private final ValidateOverlapUseCase validateOverlapUseCase;

    @Transactional
    public Block createBlock(BlockRequest request){
        log.info("createBlock request [{}]", request.toString());

        validateOverlapUseCase.validate(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                null);

        return blockRepository.save(blockMapper.toEntity(request));
    }

    @Transactional(readOnly = true)
    public List<Block> getAllBlocks(){
        return blockRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Block getBlockById(Long id){
        log.info("getBlockById id [{}]", id);
        return blockRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("getBlockById: Block not found with id: {}", id);
                    return new BlockNotFoundException("Block not found with id: " + id);
                });
    }

    @Transactional
    public Block updateBlock(Long id, BlockRequest request) {
        log.info("updateBlock with id [{}] request [{}]", id, request);

        validateOverlapUseCase.validate(request.getPropertyId(),
                request.getStartDate(),
                request.getEndDate(),
                id);

        Block existing = getBlockById(id);
        Block updated = blockMapper.updateEntity(existing, request);

        return blockRepository.save(updated);
    }

    @Transactional
    public void deleteBlock(Long id) {
        log.info("deleteBlock with id [{}]", id);
        Block existing = getBlockById(id);
        blockRepository.delete(existing);
    }

}
