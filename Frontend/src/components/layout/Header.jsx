import React from "react";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

export default function Header({name, email}) {
  
  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <header style={{ padding: 12, background: "transparent" }}>
      <div style={{
        maxWidth: 980, margin: "0 auto", padding: "6px 20px",
        display: "flex", alignItems: "center", justifyContent: "space-between"
      }}>
        <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
          <Link to="/dashboard" style={{ textDecoration: "none", color: "#0f172a", fontWeight: 800, fontSize: 18 }}>
            Todo Vite
          </Link>
          <div style={{ color: "#64748b", fontSize: 13 }}>Organize. Focus. Done.</div>
        </div>

        <div style={{ display: "flex", alignItems: "center", gap: 10 }}>
          <div style={{ fontSize: 13, color: "#475569", textTransform: "lowercase" }}>{name}</div>
          <button onClick={handleLogout} style={{
            padding: "8px 12px", borderRadius: 10, border: "1px solid #e6edf6", background: "white", cursor: "pointer", fontSize: 13
          }}>
            Logout
          </button>
        </div>
      </div>
    </header>
  );
}
