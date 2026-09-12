import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import FlightCard from "../components/FlightCard";


function FlightSearchPage() {
    const [source, setSource] = useState("");
    const [destination, setDestination] = useState("");
    const [date, setDate] = useState("");
    const [flights, setFlights] = useState([]);
    const [error, setError] = useState("");
    const [searched, setSearched] = useState(false);

    const navigate = useNavigate();

    const handleSearch = async (e) => {
        e.preventDefault();
        setError("");
        try {
            const response = await axiosClient.get("/flights/search", {
                params: { source, destination, date },
            });
            setFlights(response.data);
            setSearched(true);
        } catch (err) {
            setError(err.response?.data?.message || "Search failed.");
        }
    };

    const goToSeatMap = (flightId) => {
        navigate(`/flights/${flightId}/seats`);
    };

    return (
        <div style={{ maxWidth: 600, margin: "40px auto" }}>
            <h2>Search Flights</h2>

            <form onSubmit={handleSearch} style={{ marginBottom: 24 }}>
                <input
                    type="text"
                    value={source}
                    onChange={(e) => setSource(e.target.value)}
                    placeholder="From (e.g. Delhi)"
                    required
                    style={{ marginRight: 8 }}
                />
                <input
                    type="text"
                    value={destination}
                    onChange={(e) => setDestination(e.target.value)}
                    placeholder="To (e.g. Mumbai)"
                    required
                    style={{ marginRight: 8 }}
                />
                <input
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    required
                    style={{ marginRight: 8 }}
                />
                <button type="submit">Search</button>
            </form>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {searched && flights.length === 0 && !error && (
                <p>No flights found for that route/date.</p>
            )}

            <div>
                {flights.map((flight) => (
                    <FlightCard
                        key={flight.id}
                        flight={flight}
                        onClick={() => goToSeatMap(flight.id)}
                    />
                ))}
            </div>

        </div>
    );
}

export default FlightSearchPage;