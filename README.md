# Airline Booking System

Full-stack flight booking app with concurrency-safe seat reservation.

## Stack
- **Backend:** Spring Boot 4.1.1, Java 21, Spring Security (JWT), Spring Data JPA, PostgreSQL
- **Frontend:** React (Vite), React Router, Axios
- **Infra:** Docker, Docker Compose

## Architecture
Monolithic backend, layered (Controller → Service → Repository), stateless JWT auth.


## The interesting part: concurrency-safe booking
Two users can never book the same seat, even simultaneously. `SeatRepository`
locks the seat row with `PESSIMISTIC_WRITE` inside a `@Transactional` method —
the second concurrent request waits for the first transaction to commit, then
sees the seat as unavailable and fails cleanly with a 409, instead of both
requests succeeding and double-booking the seat.

## Running locally

1. Copy `.env.example` to `.env` and fill in your own values
2. From the repo root: docker compose up --build
3. Backend: `http://localhost:8080`
4. Frontend: `http://localhost:5173`

## API overview
| Method | Path | Auth |
|---|---|---|
| POST | `/api/auth/register` | none |
| POST | `/api/auth/login` | none |
| GET | `/api/flights/search` | user |
| GET | `/api/flights/{id}/seats` | user |
| POST | `/api/admin/flights` | admin |
| POST | `/api/bookings` | user |
| GET | `/api/bookings/my` | user |
| DELETE | `/api/bookings/{id}` | user |
