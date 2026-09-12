function FlightCard({ flight, onClick }) {
    return (
        <div
            onClick={onClick}
            style={{
                border: "1px solid #ccc",
                borderRadius: 6,
                padding: 12,
                marginBottom: 10,
                cursor: "pointer",
            }}
        >
            <strong>{flight.flightNumber}</strong>
            <p>
                {flight.source} → {flight.destination}
            </p>
            <p>
                Departure: {new Date(flight.departureTime).toLocaleString()}
            </p>
            <p>Price: ₹{flight.price}</p>
        </div>
    );
}

export default FlightCard;