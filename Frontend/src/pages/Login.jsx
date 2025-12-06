import React, { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./login.css";
import axios from "axios";

export default function Login(){
  const nav = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/dashboard";

  const [form, setForm] = useState({ email: "", password: "" });
  const [err, setErr] = useState("");
  const [showPw, setShowPw] = useState(false);
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setErr("");
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email: form.email,
        password: form.password
      });
      localStorage.setItem("token", response.data.token);
      console.log(response);
      nav("/dashboard");
    } catch (error) {
      setErr(error?.message || "Login failed");
      setLoading(false);
    }
  };

  return (
    <div className="login-root">
      <div className="login-card" role="main">
        <div className="login-body">
          <div className="brand">
            <div className="logo">TV</div>
            <div>
              <h1 className="h1">Welcome back</h1>
              <div className="subtitle">Sign in to continue to your todos</div>
            </div>
          </div>

          {err && <div className="error" role="alert">{err}</div>}

          <form onSubmit={submit}>
            <div className="form-row">
              <input
                className="input"
                placeholder="Email address"
                type="email"
                required
                value={form.email}
                onChange={e => setForm({...form, email: e.target.value})}
              />
            </div>

            <div className="form-row" style={{ position: "relative" }}>
              <input
                className="input"
                placeholder="Password"
                type={showPw ? "text" : "password"}
                required
                value={form.password}
                onChange={e => setForm({...form, password: e.target.value})}
              />
              <button
                type="button"
                onClick={() => setShowPw(s => !s)}
                style={{
                  position: "absolute", right: 10, top: "50%", transform: "translateY(-50%)",
                  background: "transparent", border: "none", cursor: "pointer", color: "#475569"
                }}
              >
                {showPw ? "Hide" : "Show"}
              </button>
            </div>

            <div className="row-between">
              <label style={{display:"flex", alignItems:"center", gap:8}}>
                <input type="checkbox" /> <span>Remember me</span>
              </label>
              <Link to="#" style={{ color: "#6d28d9", textDecoration: "underline" }}>Forgot?</Link>
            </div>

            <div style={{ marginBottom: 10 }}>
              <button className="primary-btn" type="submit" disabled={loading}>
                {loading ? "Signing in..." : "Sign in"}
              </button>
            </div>
          </form>

          <div className="divider">
            <div className="line" />
            <div className="or">or continue with</div>
            <div className="line" />
          </div>

          <div className="social-grid" role="group" aria-label="social sign in">
            <button className="social-btn" type="button">Google</button>
            <button className="social-btn" type="button">GitHub</button>
          </div>
        </div>

        <div className="footer">
          Don't have an account? <Link to="/register" style={{ color: "#6d28d9", textDecoration: "underline" }}>Create one</Link>
        </div>
      </div>
    </div>
  );
}
