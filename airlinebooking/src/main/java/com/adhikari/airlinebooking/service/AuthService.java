package com.adhikari.airlinebooking.service;

import com.adhikari.airlinebooking.dto.AuthResponse;
import com.adhikari.airlinebooking.dto.LoginRequest;
import com.adhikari.airlinebooking.dto.RegisterRequest;
import com.adhikari.airlinebooking.entity.Role;
import com.adhikari.airlinebooking.entity.User;
import com.adhikari.airlinebooking.exception.EmailAlreadyExistsException;
import com.adhikari.airlinebooking.exception.InvalidCredentialsException;
import com.adhikari.airlinebooking.repository.UserRepository;
import com.adhikari.airlinebooking.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        User user = new User();

        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        return new AuthResponse(
                token,
                savedUser.getName(),
                savedUser.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                req.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(
                token,
                user.getName(),
                user.getRole().name()
        );
    }
}