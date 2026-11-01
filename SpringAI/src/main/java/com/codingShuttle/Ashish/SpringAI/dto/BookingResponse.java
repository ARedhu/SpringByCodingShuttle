package com.codingShuttle.Ashish.SpringAI.dto;

import com.codingShuttle.Ashish.SpringAI.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    Long id;
    String destination;
    Instant departureTime;
    BookingStatus bookingStatus;
}