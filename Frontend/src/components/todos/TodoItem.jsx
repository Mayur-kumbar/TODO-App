import React, { useState } from "react";
import "./TodoItem.css";
import axios from "axios";

export default function TodoItem({ todo, onToggle, onUpdate, onDelete }) {
  const [editing, setEditing] = useState(false);
  const [title, setTitle] = useState(todo.title);
  const [description, setDescription] = useState(todo.description || "");

  const handleUpdate = async () => {
    const res = await axios.patch(`http://localhost:8080/api/todos/${todo.id}`,
      {title, description},
      { headers: {Authorization:
        `Bearer ${localStorage.getItem("token")}`,
        withCredentials: true
      }});
    
      onUpdate(todo.id, res.data);
      setEditing(false);
  };

  const handleDelete = async () => {
    await axios.delete(`http://localhost:8080/api/todos/${todo.id}`,
      { headers: {Authorization:
        `Bearer ${localStorage.getItem("token")}`,
        withCredentials: true
      }});
    
      onDelete(todo.id);
  };

  const handleCompleteToggle = async () => {
    const res = await axios.patch(`http://localhost:8080/api/todos/${todo.id}`,
      {title, description, completed: true },
      { headers: {Authorization:
        `Bearer ${localStorage.getItem("token")}`,
        withCredentials: true
      }});
    
      console.log("Toggled completion:", res.data);
      onUpdate(todo.id, res.data);
  };

  return (
    <li className="todo-item">
      <div className="todo-left">
        <input
          type="checkbox"
          checked={todo.completed}
          onChange={() => handleCompleteToggle()}
          className="todo-checkbox"
        />

        {/* Content */}
        <div className="todo-content">
          {editing ? (
            <div className="edit-group">
              <input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className="edit-input"
                placeholder="Title"
              />
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="edit-textarea"
                placeholder="Description"
              />
            </div>
          ) : (
            <div>
              <div className={`todo-title ${todo.done ? "done" : ""}`}>
                {todo.title}
              </div>

              {todo.description && (
                <div className="todo-description">{todo.description}</div>
              )}
            </div>
          )}
        </div>
      </div>

      <div className="todo-actions">
        {editing ? (
          <button onClick={handleUpdate} className="btn">
            Save
          </button>
        ) : (
          <button onClick={() => setEditing(true)} className="btn">
            Edit
          </button>
        )}

        <button onClick={handleDelete} className="btn delete-btn">
          Delete
        </button>
      </div>
    </li>
  );
}
