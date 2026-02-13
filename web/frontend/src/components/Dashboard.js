import React, { useEffect, useState } from "react";
import API from "../services/api";
import "./Dashboard.css";

function Dashboard() {
  const [user, setUser] = useState(null);

  const fetchUser = async () => {
    const username = localStorage.getItem("username");
    if (!username) return;
    try {
      const res = await API.get(`/api/auth/me?username=${username}`);
      setUser(res.data);
    } catch {
      setUser(null);
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  return (
    <div className="dashboard-container">
      <h2>Dashboard/Profile</h2>
      {user ? (
        <div>
          <p><b>Username:</b> {user.username}</p>
          <p><b>Email:</b> {user.email}</p>
        </div>
      ) : (
        <p>Loading user info...</p>
      )}
    </div>
  );
}

export default Dashboard;