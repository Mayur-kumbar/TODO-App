import React, { useEffect, useState } from "react";
import Header from "../components/layout/Header";
import TaskForm from "../components/todos/TaskForm";
import TodoList from "../components/todos/TodoList";
import "../pages/dashboard.css";
import axios from "axios";


export default function Dashboard(){
  const [todos, setTodos] = useState([]);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  useEffect(() => {
    if(!localStorage.getItem("token")){
      window.location.href = "/login";
      return;
    }
    const fetchTodos = async () => {
      const res = await axios.get("http://localhost:8080/api/todos",
        { headers: {Authorization:
          `Bearer ${localStorage.getItem("token")}`,
          withCredentials: true
        }
}
      );
      setTodos(res.data);
      console.log(res.data);
    }

    const fetchUserData = async () => {
      const res = await axios.get("http://localhost:8080/api/user",
        { headers: {Authorization:
          `Bearer ${localStorage.getItem("token")}`,
          withCredentials: true
        }
}
      );
      setName(res.data.name);
      setEmail(res.data.email);
      console.log(res.data);
    }
    fetchUserData();
    fetchTodos();
  }, []);

 
  const addTodo = async ({ title, description }) => {
    const user = localStorage.getItem("user");
    console.log(user);
    if(!user){
      window.location.href = "/login";
      return;
    }
    const res = await axios.post("http://localhost:8080/api/todos", { title, description },
      { headers: {Authorization:
        `Bearer ${localStorage.getItem("token")}`,
        withCredentials: true
      }
}
    );
    setTodos(prev => [res.data, ...prev]);
    console.log(res.data);
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
      <Header name={name} email={email} />
      <main className="container">
        <div className="greeting-row">
          <div className="greeting-left">
            <div className="title">Hello, {name}</div>
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
