import { useState, useEffect } from "react";
import axiosClient from "../api/axiosClient";

function MyBookingsPage() {
    const [bookings, setBookings] = useState([]);
    const [error, setError] = useState("");

    const fetchBookings = async () => {
        try {
            const response = await axiosClient.get("/bookings/my");
            setBookings(response.data);
        } catch (err) {
            setError(err.response?.data?.message || "Could not load bookings.");
        }
    };

    useEffect(() => {
        fetchBookings();
    }, []);

    const handleCancel = async (bookingId) => {
        setError("");
        try {
            await axiosClient.delete(`/bookings/${bookingId}`);
            fetchBookings();
        } catch (err) {
            setError(err.response?.data?.message || "Cancel failed.");
        }
    };

    return (
        <div style={{ maxWidth: 600, margin: "40px auto" }}>
            <h2>My Bookings</h2>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {bookings.length === 0 && <p>You have no bookings yet.</p>}

            {bookings.map((booking) => (
                <div
                    key={booking.id}
                    style={{
                        border: "1px solid #ccc",
                        borderRadius: 6,
                        padding: 12,
                        marginBottom: 10,
                    }}
                >
                    <p>
                        <strong>Ref:</strong> {booking.bookingReference}
                    </p>
                    <p>
                        <strong>Flight:</strong> {booking.flightNumber}
                    </p>
                    <p>
                        <strong>Seat:</strong> {booking.seatNumber}
                    </p>
                    <p>
                        <strong>Status:</strong> {booking.status}
                    </p>
                    {booking.status === "CONFIRMED" && (
                        <button onClick={() => handleCancel(booking.id)}>
                            Cancel Booking
                        </button>
                    )}
                </div>
            ))}
        </div>
    );
}

export default MyBookingsPage;