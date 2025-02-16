package com.github.lunodesouza.bookingservice.repository;

import com.github.lunodesouza.bookingservice.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
