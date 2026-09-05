package com.adhikari.airlinebooking.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String source;
    private String destination;
    private LocalDateTime  departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal price;
    private int totalSeats;

    @OneToMany(mappedBy = "flight")
    private List<Seat> seats;

}
