import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import "./Form.css";

function Login({ setIsAuthenticated }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post("/api/auth/login", form);
      setMessage(res.data);
      if (res.data.includes("successful")) {
        localStorage.setItem("username", form.username);
        setIsAuthenticated(true);
        navigate("/dashboard");
      }
    } catch {
      setMessage("Login failed");
    }
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Login</h2>
      <form onSubmit={handleSubmit} className="form">
        <input type="text" name="username" placeholder="Username" className="form-input" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" className="form-input" onChange={handleChange} required />
        <button type="submit" className="form-btn">Login</button>
      </form>
      {message && <p className="form-message">{message}</p>}
    </div>
  );
}

export default Login;