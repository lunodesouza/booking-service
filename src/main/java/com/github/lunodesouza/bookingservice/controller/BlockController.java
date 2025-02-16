package com.github.lunodesouza.bookingservice.controller;

import com.github.lunodesouza.bookingservice.dto.BlockRequest;
import com.github.lunodesouza.bookingservice.dto.BlockResponse;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.service.BlockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/blocks")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;
    private final BlockMapper blockMapper;

    @PostMapping
    public ResponseEntity<BlockResponse> createBlock(
            @Valid @RequestBody BlockRequest blockRequest){

        Block block = blockService.createBlock(blockRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockMapper.toResponse(block));
    }

    @GetMapping
    public ResponseEntity<List<BlockResponse>> getAllBlocks() {
        List<Block> blocks = blockService.getAllBlocks();
        return ResponseEntity.ok(blockMapper.toResponseList(blocks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlockResponse> getBlockById(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);
        return ResponseEntity.ok(blockMapper.toResponse(block));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlockResponse> updateBlock(
            @PathVariable Long id,
            @Valid @RequestBody BlockRequest request
    ) {
        Block updatedBlock = blockService.updateBlock(id, request);
        return ResponseEntity.ok(blockMapper.toResponse(updatedBlock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.noContent().build();
    }
}
