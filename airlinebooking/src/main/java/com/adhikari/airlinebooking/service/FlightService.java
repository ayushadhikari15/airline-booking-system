package com.adhikari.airlinebooking.service;

import com.adhikari.airlinebooking.dto.FlightCreateRequest;
import com.adhikari.airlinebooking.dto.FlightResponse;
import com.adhikari.airlinebooking.dto.SeatResponse;
import com.adhikari.airlinebooking.entity.Flight;
import com.adhikari.airlinebooking.entity.Seat;
import com.adhikari.airlinebooking.entity.SeatStatus;
import com.adhikari.airlinebooking.repository.FlightRepository;
import com.adhikari.airlinebooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    public FlightResponse createFlight(FlightCreateRequest req) {

        Flight flight = new Flight();

        flight.setFlightNumber(req.getFlightNumber());
        flight.setSource(req.getSource());
        flight.setDestination(req.getDestination());
        flight.setDepartureTime(req.getDepartureTime());
        flight.setArrivalTime(req.getArrivalTime());
        flight.setPrice(req.getPrice());
        flight.setTotalSeats(req.getTotalSeats());

        Flight savedFlight = flightRepository.save(flight);

        for (int i = 1; i <= req.getTotalSeats(); i++) {

            int row = (i - 1) / 4 + 1;
            char letter = (char) ('A' + (i - 1) % 4);

            Seat seat = new Seat();

            seat.setSeatNumber(row + String.valueOf(letter));
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setFlight(savedFlight);

            seatRepository.save(seat);
        }

        return mapToFlightResponse(savedFlight);
    }

    public List<FlightResponse> searchFlights(
            String source,
            String destination,
            LocalDate date
    ) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);

        List<Flight> flights =
                flightRepository.findBySourceAndDestinationAndDepartureTimeBetween(
                        source,
                        destination,
                        start,
                        end
                );

        return flights.stream()
                .map(this::mapToFlightResponse)
                .toList();
    }

    public List<SeatResponse> getSeats(Long flightId) {

        List<Seat> seats = seatRepository.findByFlightId(flightId);

        return seats.stream()
                .map(this::mapToSeatResponse)
                .toList();
    }

    private FlightResponse mapToFlightResponse(Flight flight) {

        return new FlightResponse(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice(),
                flight.getTotalSeats()
        );
    }

    private SeatResponse mapToSeatResponse(Seat seat) {

        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getStatus().name()
        );
    }
}
