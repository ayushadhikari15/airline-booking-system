function SeatGrid({ seats, onSeatClick, disabled }) {
    return (
        <div
            style={{
                display: "grid",
                gridTemplateColumns: "repeat(4, 1fr)",
                gap: 10,
            }}
        >
            {seats.map((seat) => {
                const isAvailable = seat.status === "AVAILABLE";
                return (
                    <button
                        key={seat.id}
                        disabled={!isAvailable || disabled}
                        onClick={() => onSeatClick(seat.id)}
                        style={{
                            padding: 12,
                            borderRadius: 6,
                            border: "1px solid #999",
                            backgroundColor: isAvailable ? "#d4f5dd" : "#f0f0f0",
                            color: isAvailable ? "#000" : "#999",
                            cursor: isAvailable ? "pointer" : "not-allowed",
                        }}
                    >
                        {seat.seatNumber}
                    </button>
                );
            })}
        </div>
    );
}

export default SeatGrid;