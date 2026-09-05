package com.adhikari.airlinebooking.service;

import com.adhikari.airlinebooking.dto.BookingRequest;
import com.adhikari.airlinebooking.dto.BookingResponse;
import com.adhikari.airlinebooking.entity.Booking;
import com.adhikari.airlinebooking.entity.BookingStatus;
import com.adhikari.airlinebooking.entity.Seat;
import com.adhikari.airlinebooking.entity.SeatStatus;
import com.adhikari.airlinebooking.entity.User;
import com.adhikari.airlinebooking.exception.ResourceNotFoundException;
import com.adhikari.airlinebooking.exception.SeatUnavailableException;
import com.adhikari.airlinebooking.exception.UnauthorizedActionException;
import com.adhikari.airlinebooking.repository.BookingRepository;
import com.adhikari.airlinebooking.repository.SeatRepository;
import com.adhikari.airlinebooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse bookSeat(Long userId, BookingRequest req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        Seat seat = seatRepository.findByIdForUpdate(req.getSeatId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found")
                );

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new SeatUnavailableException(
                    "Seat " + seat.getSeatNumber() + " is not available"
            );
        }

        Booking booking = new Booking();

        booking.setBookingReference(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
        );

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookedAt(LocalDateTime.now());
        booking.setUser(user);
        booking.setFlight(seat.getFlight());
        booking.setSeat(seat);

        seat.setStatus(SeatStatus.BOOKED);

        seatRepository.save(seat);
        Booking savedBooking = bookingRepository.save(booking);

        return mapToBookingResponse(savedBooking);
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId, Long userId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found")
                );

        if (!booking.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException(
                    "You are not allowed to cancel this booking"
            );
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new SeatUnavailableException(
                    "Booking is already cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Seat seat = booking.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);

        seatRepository.save(seat);
        Booking savedBooking = bookingRepository.save(booking);

        return mapToBookingResponse(savedBooking);
    }

    public List<BookingResponse> getMyBookings(Long userId) {

        List<Booking> bookings =
                bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::mapToBookingResponse)
                .toList();
    }

    private BookingResponse mapToBookingResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setStatus(booking.getStatus().name());
        response.setFlightNumber(
                booking.getFlight().getFlightNumber()
        );
        response.setSeatNumber(
                booking.getSeat().getSeatNumber()
        );

        return response;
    }
}