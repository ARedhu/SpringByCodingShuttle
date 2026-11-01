package com.codingShuttle.Ashish.SpringAI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingsListResponse {

    List<BookingResponse> bookingResponseList;
    String message;
}
