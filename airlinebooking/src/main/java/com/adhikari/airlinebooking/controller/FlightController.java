package com.adhikari.airlinebooking.controller;

import com.adhikari.airlinebooking.dto.FlightCreateRequest;
import com.adhikari.airlinebooking.dto.FlightResponse;
import com.adhikari.airlinebooking.dto.SeatResponse;
import com.adhikari.airlinebooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/flights/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(
                flightService.searchFlights(
                        source,
                        destination,
                        date
                )
        );
    }

    @GetMapping("/flights/{id}/seats")
    public ResponseEntity<List<SeatResponse>> getSeats(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                flightService.getSeats(id)
        );
    }

    @PostMapping("/admin/flights")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponse> createFlight(
            @RequestBody FlightCreateRequest request
    ) {
        return ResponseEntity.ok(
                flightService.createFlight(request)
        );
    }
}
