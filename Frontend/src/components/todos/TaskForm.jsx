import React, { useState } from "react";
import "../../pages/dashboard.css";

export default function TaskForm({ onAdd }){
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const submit = (e) => {
    e.preventDefault();
    if(!title.trim()) return;
    onAdd({ title: title.trim(), description: description || null });
    setTitle("");
    setDescription("");
  };

  return (
    <form onSubmit={submit} className="task-form" aria-label="Add task">
      <input
        className="task-input"
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Add a new task..."
        aria-label="Task title"
      />
      <input
        className="date-input"
        value={description}
        onChange={e => setDescription(e.target.value)}
        type="text"
        aria-label="description"
        placeholder="description"
      />
      <button className="add-btn" type="submit">Add</button>
    </form>
  );
}
