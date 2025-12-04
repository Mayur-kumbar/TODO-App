import React, { useState, useMemo } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./register.css";

export default function Register(){
  const { register } = useAuth();
  const nav = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    confirm: ""
  });
  const [err, setErr] = useState("");
  const [fieldErrs, setFieldErrs] = useState({});
  const [showPw, setShowPw] = useState(false);
  const [loading, setLoading] = useState(false);

  const minPwLen = 6;

  const validate = () => {
    const e = {};

    if (!form.name.trim()) e.name = "Name is required";
    if (!form.email.trim()) e.email = "Email is required";
    else if (!/^\S+@\S+\.\S+$/.test(form.email))
      e.email = "Enter a valid email";

    if (!form.password) e.password = "Password is required";
    else if (form.password.length < minPwLen)
      e.password = `Must be at least ${minPwLen} characters`;

    if (form.password !== form.confirm)
      e.confirm = "Passwords do not match";

    setFieldErrs(e);
    return Object.keys(e).length === 0;
  };

  // simple password strength
  const strength = useMemo(() => {
    let score = 0;
    const pw = form.password;
    if (pw.length >= minPwLen) score++;
    if (/[A-Z]/.test(pw)) score++;
    if (/[0-9]/.test(pw)) score++;
    if (/[^A-Za-z0-9]/.test(pw)) score++;
    return score; // 0–4
  }, [form.password]);

  const strengthColors = ["#ef4444", "#f97316", "#facc15", "#4ade80"];
  const strengthLabels = ["Very weak", "Weak", "Okay", "Strong"];

  const submit = async (e) => {
    e.preventDefault();
    setErr("");

    if (!validate()) return;

    setLoading(true);

    try {
      await register({
        name: form.name.trim(),
        email: form.email.trim(),
        password: form.password
      });
      nav("/dashboard");
    } catch (error) {
      setErr(error.message || "Registration failed");
      setLoading(false);
    }
  };

  return (
    <div className="register-root">
      <div className="register-card">
        <div className="register-body">

          <div className="brand">
            <div className="logo">TV</div>
            <div>
              <h1 className="h1">Create account</h1>
              <div className="subtitle">
                Join and start organizing your tasks easily.
              </div>
            </div>
          </div>

          {err && <div className="error">{err}</div>}

          <form onSubmit={submit}>

            <div className="form-row">
              <input
                className="input"
                placeholder="Full Name"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
              />
              {fieldErrs.name && (
                <div className="field-error">{fieldErrs.name}</div>
              )}
            </div>

            <div className="form-row">
              <input
                className="input"
                placeholder="Email address"
                type="email"
                value={form.email}
                onChange={(e) => setForm({ ...form, email: e.target.value })}
              />
              {fieldErrs.email && (
                <div className="field-error">{fieldErrs.email}</div>
              )}
            </div>

            <div className="form-row" style={{ position: "relative" }}>
              <input
                className="input"
                type={showPw ? "text" : "password"}
                placeholder="Password"
                value={form.password}
                onChange={(e) =>
                  setForm({ ...form, password: e.target.value })
                }
              />
              <button
                className="show-toggle"
                type="button"
                onClick={() => setShowPw((s) => !s)}
              >
                {showPw ? "Hide" : "Show"}
              </button>
              {fieldErrs.password && (
                <div className="field-error">{fieldErrs.password}</div>
              )}

              <div className="strength-bar">
                <div
                  className="strength-fill"
                  style={{
                    width: `${(strength / 4) * 100}%`,
                    background: strengthColors[strength - 1] || "#e2e8f0"
                  }}
                />
              </div>
              <div className="strength-text">
                {form.password
                  ? strengthLabels[strength - 1] || "Very weak"
                  : "Use at least 6 characters"}
              </div>
            </div>

            <div className="form-row">
              <input
                className="input"
                type={showPw ? "text" : "password"}
                placeholder="Confirm Password"
                value={form.confirm}
                onChange={(e) =>
                  setForm({ ...form, confirm: e.target.value })
                }
              />
              {fieldErrs.confirm && (
                <div className="field-error">{fieldErrs.confirm}</div>
              )}
            </div>

            <button className="primary-btn" disabled={loading}>
              {loading ? "Creating account..." : "Create Account"}
            </button>
          </form>
        </div>

        <div className="footer">
          Already have an account?{" "}
          <Link to="/login">Login</Link>
        </div>
      </div>
    </div>
  );
}
