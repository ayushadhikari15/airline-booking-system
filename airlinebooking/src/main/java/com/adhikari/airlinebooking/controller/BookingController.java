package com.adhikari.airlinebooking.controller;

import com.adhikari.airlinebooking.dto.BookingRequest;
import com.adhikari.airlinebooking.dto.BookingResponse;
import com.adhikari.airlinebooking.entity.User;
import com.adhikari.airlinebooking.repository.UserRepository;
import com.adhikari.airlinebooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BookingResponse> bookSeat(
            @RequestBody BookingRequest request,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);

        return ResponseEntity.ok(
                bookingService.bookSeat(
                        user.getId(),
                        request
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);

        return ResponseEntity.ok(
                bookingService.getMyBookings(user.getId())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);

        return ResponseEntity.ok(
                bookingService.cancelBooking(
                        id,
                        user.getId()
                )
        );
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}