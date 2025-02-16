package com.github.lunodesouza.bookingservice.repository;

import com.github.lunodesouza.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
