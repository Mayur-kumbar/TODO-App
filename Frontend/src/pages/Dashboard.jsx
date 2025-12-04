import React, { useEffect, useState } from "react";
import Header from "../components/layout/Header";
import TaskForm from "../components/todos/TaskForm";
import TodoList from "../components/todos/TodoList";
import { useAuth } from "../context/AuthContext";
import "../pages/dashboard.css";

/* storage key function */
const todosKey = (email) => `todos_${email}`;

function generateId() {
  if (typeof crypto !== "undefined" && crypto.randomUUID) {
    return crypto.randomUUID();
  }
  return "id_" + Date.now().toString(36) + Math.random().toString(36).slice(2,8);
}

export default function Dashboard(){
  const { user } = useAuth();
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    if(user){
      const raw = localStorage.getItem(todosKey(user.email));
      setTodos(raw ? JSON.parse(raw) : []);
    }
  }, [user]);

  useEffect(() => {
    if(user){
      localStorage.setItem(todosKey(user.email), JSON.stringify(todos));
    }
  }, [todos, user]);

  const addTodo = ({ title, dueDate }) => {
    setTodos(prev => [{ id: generateId(), title, done: false, dueDate: dueDate || null }, ...prev]);
  };

  const toggleTodo = (id) => {
    setTodos(prev => prev.map(t => t.id === id ? { ...t, done: !t.done } : t));
  };

  const updateTodo = (id, newData) => {
    setTodos(prev => prev.map(t => t.id === id ? { ...t, ...newData } : t));
  };

  const deleteTodo = (id) => {
    setTodos(prev => prev.filter(t => t.id !== id));
  };

  return (
    <div className="dashboard-root">
      <Header />
      <main className="container">
        <div className="greeting-row">
          <div className="greeting-left">
            <div className="title">Hello, {user?.name}</div>
            <div className="subtitle">Here's your tasks for today</div>
          </div>
        </div>

        <div className="card">
          <TaskForm onAdd={addTodo} />
          <TodoList todos={todos} onToggle={toggleTodo} onUpdate={updateTodo} onDelete={deleteTodo} />
        </div>
      </main>
    </div>
  );
}
