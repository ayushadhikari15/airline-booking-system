import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import FlightSearchPage from "./pages/FlightSearchPage";
import SeatMapPage from "./pages/SeatMapPage";
import MyBookingsPage from "./pages/MyBookingsPage";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";


function App() {
    return (
        <>
            <Navbar/>
        <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />

            <Route
                path="/flights"
                element={
                    <ProtectedRoute>
                        <FlightSearchPage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/flights/:flightId/seats"
                element={
                    <ProtectedRoute>
                        <SeatMapPage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/bookings"
                element={
                    <ProtectedRoute>
                        <MyBookingsPage />
                    </ProtectedRoute>
                }
            />

            <Route path="/" element={<Navigate to="/flights" replace />} />
        </Routes>
        </>
    );
}

export default App;