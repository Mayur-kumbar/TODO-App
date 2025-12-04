import React, { useState } from "react";
import "../../pages/dashboard.css";

export default function TaskForm({ onAdd }){
  const [title, setTitle] = useState("");
  const [dueDate, setDueDate] = useState("");

  const submit = (e) => {
    e.preventDefault();
    if(!title.trim()) return;
    onAdd({ title: title.trim(), dueDate: dueDate || null });
    setTitle("");
    setDueDate("");
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
        value={dueDate}
        onChange={e => setDueDate(e.target.value)}
        type="date"
        aria-label="Due date"
      />
      <button className="add-btn" type="submit">Add</button>
    </form>
  );
}
