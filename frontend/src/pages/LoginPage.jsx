import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function LoginPage() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            await login( email, password);
            navigate("/flights");  // or wherever your logged-in landing page is
        } catch (err) {
            setError(
                err.response?.data?.message || "Login failed. Try again."
            );
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Log in</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}

            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
                required
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                required
            />
            <button type="submit">Log in</button>
        </form>
    );
}

export default LoginPage;