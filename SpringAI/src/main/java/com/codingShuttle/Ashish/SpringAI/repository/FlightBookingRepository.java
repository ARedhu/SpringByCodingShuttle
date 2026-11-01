package com.codingShuttle.Ashish.SpringAI.repository;

import com.codingShuttle.Ashish.SpringAI.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {

    List<FlightBooking> findByUserIdOrderByDepartureTimeDesc(String userId);
    boolean existsByUserIdAndDestinationAndDepartureTime(String userId, String destination, Instant departureTime);
}
