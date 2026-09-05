package com.adhikari.airlinebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private String bookingReference;
    private String status;
    private String flightNumber;
    private String seatNumber;
}
