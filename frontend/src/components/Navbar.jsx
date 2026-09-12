import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
    const { isAuthenticated, user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    if (!isAuthenticated) {
        return null; // no navbar on login/register screens
    }

    return (
        <nav
            style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                padding: "12px 24px",
                borderBottom: "1px solid #ddd",
            }}
        >
            <div style={{ display: "flex", gap: 16 }}>
                <Link to="/flights">Search Flights</Link>
                <Link to="/bookings">My Bookings</Link>
            </div>
            <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
                <span>{user?.name}</span>
                <button onClick={handleLogout}>Logout</button>
            </div>
        </nav>
    );
}

export default Navbar;