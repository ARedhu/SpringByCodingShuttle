package com.codingShuttle.Ashish.SpringAI.tool;

import com.codingShuttle.Ashish.SpringAI.dto.BookingResponse;
import com.codingShuttle.Ashish.SpringAI.dto.BookingsListResponse;
import com.codingShuttle.Ashish.SpringAI.entity.BookingStatus;
import com.codingShuttle.Ashish.SpringAI.entity.FlightBooking;
import com.codingShuttle.Ashish.SpringAI.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {

    private final FlightBookingService flightBookingService;

    @Tool(
            name = "flight_booking_tool",
            description = "Create a new flight booking for a user"
    )
    public BookingResponse createBooking(
            @ToolParam(description = "The unique user id (e.g. userId is user123)")
            String userId,

            @ToolParam(description = "The destination for the flight booking (e.g. city like Delhi, Mumbai, etc.)")
            String destination,

            @ToolParam(description = "Departure date and time in ISO-8601 format (e.g., 2025-12-25T14:30:00Z")
            Instant departureTime
    ){
        var flightBooking = flightBookingService.createBooking(userId, destination, departureTime);
        return new BookingResponse(
                flightBooking.getId(),
                flightBooking.getDestination(),
                flightBooking.getDepartureTime(),
                flightBooking.getBookingStatus()
        );

    }


    @Tool(
            name = "get_user_bookings",
            description = "Retrieve all flight bookings belonging to a specific user, order by departure time in descending order"
    )
    public BookingsListResponse getUserBookings(
            @ToolParam(description = "Unique ID of the user whose booking should be retrieved")
            String userId
    ){
        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);
        List<BookingResponse> responses = bookings
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getDestination(),
                        booking.getDepartureTime(),
                        booking.getBookingStatus()
                ))
                .toList();

        String message = bookings.isEmpty()
                ? "You have no upcoming flight booking."
                : "Here are your current flight bookings: ";

        return new BookingsListResponse(responses, message);
    }



    @Tool(
            name = "update_booking_status",
            description = "Update the status of an existing flight booking belonging to a specific user"
    )
    public BookingResponse updateBookingStatus(
            @ToolParam(description = "Unique ID of the booking to update")
            Long bookingId,

            @ToolParam(description = "Unique ID of the user who  owns this booking")
            String userId,

            @ToolParam(description = "New booking status. Allowed values: CONFIRMED, CANCELLED")
            BookingStatus newStatus
    ){
        var booking = flightBookingService.updateBookingStatus(bookingId, userId, newStatus);
        return new BookingResponse(
                booking.getId(),
                booking.getDestination(),
                booking.getDepartureTime(),
                booking.getBookingStatus()
        );
    }
}
