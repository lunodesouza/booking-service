package com.github.lunodesouza.bookingservice.service;

import com.github.lunodesouza.bookingservice.dto.BlockRequest;
import com.github.lunodesouza.bookingservice.exception.BlockNotFoundException;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public Block createBlock(BlockRequest blockRequest){
        return blockRepository.save(blockMapper.toEntity(blockRequest));
    }

    public List<Block> getAllBlocks(){
        return blockRepository.findAll();
    }

    public Block getBlockById(Long id){
        return blockRepository.findById(id)
                .orElseThrow(() -> new BlockNotFoundException("Block not found with id: " + id));
    }

    public Block updateBlock(Long id, BlockRequest request) {
        Block existing = getBlockById(id);
        Block updated = blockMapper.updateEntity(existing, request);
        return blockRepository.save(updated);
    }

    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }

}
