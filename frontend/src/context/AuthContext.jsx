import { createContext, useContext, useState } from "react";
import axiosClient from "../api/axiosClient";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [user, setUser] = useState(() => {
        const stored = localStorage.getItem("user");
        return stored ? JSON.parse(stored) : null;
    });

    const login = async (email, password) => {
        const response = await axiosClient.post("/auth/login", {
            email,
            password,
        });

        const { token: newToken, name, role } = response.data;
        const userData = { name, role };

        localStorage.setItem("token", newToken);
        localStorage.setItem("user", JSON.stringify(userData));

        setToken(newToken);
        setUser(userData);
    };

    const register = async (name, email, password) => {
        const response = await axiosClient.post("/auth/register", {
            name,
            email,
            password,
        });

        const { token: newToken, name: userName, role } = response.data;
        const userData = { name: userName, role };

        localStorage.setItem("token", newToken);
        localStorage.setItem("user", JSON.stringify(userData));

        setToken(newToken);
        setUser(userData);
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setToken(null);
        setUser(null);
    };

    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider
            value={{ token, user, login, register, logout, isAuthenticated }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}