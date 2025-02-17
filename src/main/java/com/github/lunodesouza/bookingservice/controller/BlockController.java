package com.github.lunodesouza.bookingservice.controller;

import com.github.lunodesouza.bookingservice.dto.request.BlockRequest;
import com.github.lunodesouza.bookingservice.dto.response.BlockResponse;
import com.github.lunodesouza.bookingservice.mapper.BlockMapper;
import com.github.lunodesouza.bookingservice.model.Block;
import com.github.lunodesouza.bookingservice.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/blocks")
@RequiredArgsConstructor
@Tag(name = "Blocks", description = "Operations for managing property blocks")
public class BlockController {
    private final BlockService blockService;
    private final BlockMapper blockMapper;

    @PostMapping
    @Operation(summary = "Create a block", description = "Create a new property block period")
    @ApiResponse(responseCode = "201", description = "Block created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Date conflict with existing booking/block")
    public ResponseEntity<BlockResponse> createBlock(
            @Valid @RequestBody BlockRequest blockRequest){

        Block block = blockService.createBlock(blockRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockMapper.toResponse(block));
    }

    @GetMapping
    @Operation(summary = "Get all blocks", description = "Retrieve list of all property blocks")
    @ApiResponse(responseCode = "200", description = "Blocks retrieved successfully")
    public ResponseEntity<List<BlockResponse>> getAllBlocks() {
        List<Block> blocks = blockService.getAllBlocks();
        return ResponseEntity.ok(blockMapper.toResponseList(blocks));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get block by ID", description = "Retrieve a specific block by ID")
    @ApiResponse(responseCode = "200", description = "Block found")
    @ApiResponse(responseCode = "404", description = "Block not found")
    public ResponseEntity<BlockResponse> getBlockById(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);
        return ResponseEntity.ok(blockMapper.toResponse(block));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update block", description = "Update an existing property block")
    @ApiResponse(responseCode = "200", description = "Block updated successfully")
    @ApiResponse(responseCode = "404", description = "Block not found")
    @ApiResponse(responseCode = "409", description = "Date conflict with existing booking/block")
    public ResponseEntity<BlockResponse> updateBlock(
            @PathVariable Long id,
            @Valid @RequestBody BlockRequest request
    ) {
        Block updatedBlock = blockService.updateBlock(id, request);
        return ResponseEntity.ok(blockMapper.toResponse(updatedBlock));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete block", description = "Delete a property block by ID")
    @ApiResponse(responseCode = "204", description = "Block deleted successfully")
    @ApiResponse(responseCode = "404", description = "Block not found")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.noContent().build();
    }
}
