package com.adhikari.airlinebooking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {

    private Long id;
    private String seatNumber;
    private String status;
}
