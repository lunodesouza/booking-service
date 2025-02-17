package com.github.lunodesouza.bookingservice.repository;

import com.github.lunodesouza.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.propertyId = :propertyId " +
            "AND b.status = 'ACTIVE' " +
            "AND (b.startDate <= :end AND b.endDate >= :start) " +
            "AND (:excludeId IS NULL OR b.id != :excludeId)")
    boolean existsConflictingBooking(
            @Param("propertyId") Long propertyId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("excludeId") Long excludeId
    );
}
