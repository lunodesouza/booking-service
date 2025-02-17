package com.github.lunodesouza.bookingservice.repository;

import com.github.lunodesouza.bookingservice.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Block b " +
            "WHERE b.propertyId = :propertyId " +
            "AND (b.startDate <= :end AND b.endDate >= :start) "+
            "AND (:excludeId IS NULL OR b.id != :excludeId)")
    boolean existsConflictingBlock(
            @Param("propertyId") Long propertyId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("excludeId") Long excludeId
    );
}
