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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocks")
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
}
