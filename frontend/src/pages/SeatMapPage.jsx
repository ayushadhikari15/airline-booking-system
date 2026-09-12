import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import SeatGrid from "../components/SeatGrid";


function SeatMapPage() {
    const { flightId } = useParams();
    const [seats, setSeats] = useState([]);
    const [error, setError] = useState("");
    const [booking, setBooking] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSeats = async () => {
            try {
                const response = await axiosClient.get(
                    `/flights/${flightId}/seats`
                );
                setSeats(response.data);
            } catch (err) {
                setError(err.response?.data?.message || "Could not load seats.");
            }
        };
        fetchSeats();
    }, [flightId]);

    const handleBookSeat = async (seatId) => {
        setError("");
        setBooking(true);
        try {
            await axiosClient.post("/bookings", { flightId, seatId });
            navigate("/bookings");
        } catch (err) {
               setError(err.response?.data?.message || "Booking failed.");
            setBooking(false);
        }
    };

    return (
        <div style={{ maxWidth: 500, margin: "40px auto" }}>
            <h2>Select a Seat</h2>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <SeatGrid seats={seats} onSeatClick={handleBookSeat} disabled={booking} />
        </div>
    );
}

export default SeatMapPage;