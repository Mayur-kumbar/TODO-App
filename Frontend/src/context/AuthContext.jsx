import React, { createContext, useContext, useState, useEffect } from "react";

// keys in localStorage
const USERS_KEY = "todo_app_users";
const SESSION_KEY = "todo_app_session";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

function readUsers(){
  const raw = localStorage.getItem(USERS_KEY);
  return raw ? JSON.parse(raw) : {};
}

function writeUsers(users){
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

export function AuthProvider({ children }){
  const [user, setUser] = useState(null);

  useEffect(() => {
    const session = localStorage.getItem(SESSION_KEY);
    if(session){
      setUser(JSON.parse(session));
    }
  }, []);

  const register = ({ name, email, password }) => {
    const users = readUsers();
    if(users[email]) {
      throw new Error("User already exists");
    }
    users[email] = { name, email, password };
    writeUsers(users);
    // auto-login
    localStorage.setItem(SESSION_KEY, JSON.stringify({ name, email }));
    setUser({ name, email });
  };

  const login = ({ email, password }) => {
    const users = readUsers();
    const u = users[email];
    if(!u || u.password !== password){
      throw new Error("Invalid credentials");
    }
    localStorage.setItem(SESSION_KEY, JSON.stringify({ name: u.name, email: u.email }));
    setUser({ name: u.name, email: u.email });
  };

  const logout = () => {
    localStorage.removeItem(SESSION_KEY);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, register, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
