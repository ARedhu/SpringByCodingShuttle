package com.codingShuttle.Ashish.SpringAI.service;

import com.codingShuttle.Ashish.SpringAI.entity.BookingStatus;
import com.codingShuttle.Ashish.SpringAI.entity.FlightBooking;
import com.codingShuttle.Ashish.SpringAI.repository.FlightBookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


// Remember we will not create tools here in the service layer. This layer is purely for business logic. We will create tools inside of a seperate tools file for better seperation of concerns.
@Service
@RequiredArgsConstructor
@Transactional
public class FlightBookingService {

    private final FlightBookingRepository flightBookingRepository;

    public FlightBooking createBooking(String userId, String destination, Instant departureTime){
        boolean exists = flightBookingRepository.existsByUserIdAndDestinationAndDepartureTime(userId, destination, departureTime);
        if(exists){
            throw new IllegalArgumentException("You already have a booking to "+destination+" on that date.");
        }

        FlightBooking booking = FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        return flightBookingRepository.save(booking);
    }

    public List<FlightBooking> getUserBookings(String userId){
        return flightBookingRepository.findByUserIdOrderByDepartureTimeDesc(userId);
    }

    public FlightBooking updateBookingStatus(Long bookingId, String userId, BookingStatus newStatus){
        FlightBooking booking = flightBookingRepository.findById(bookingId).orElseThrow(()-> new IllegalArgumentException("Booking not found"));
        if(!booking.getUserId().equals(userId)){
            throw new IllegalArgumentException("You can only modify your own bookings");
        }
        booking.setBookingStatus(newStatus);
        return flightBookingRepository.save(booking);
    }
}
