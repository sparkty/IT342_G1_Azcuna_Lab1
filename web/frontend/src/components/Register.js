import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import "./Form.css";

function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", email: "", password: "" });
  const [message, setMessage] = useState("");

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post("/api/auth/register", form);
      setMessage(res.data);
      if (res.data.includes("successfully")) setTimeout(() => navigate("/login"), 1000);
    } catch {
      setMessage("Registration failed");
    }
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Register</h2>
      <form onSubmit={handleSubmit} className="form">
        <input type="text" name="username" placeholder="Username" className="form-input" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" className="form-input" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" className="form-input" onChange={handleChange} required />
        <button type="submit" className="form-btn">Register</button>
      </form>
      {message && <p className="form-message">{message}</p>}
    </div>
  );
}

export default Register;