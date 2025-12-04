import React from "react";
import TodoItem from "./TodoItem";
import "../../pages/dashboard.css";

export default function TodoList({ todos, onToggle, onUpdate, onDelete }){
  if(!todos.length) return <div className="empty">No tasks yet — add your first task above</div>;
  return (
    <div className="todo-list" role="list">
      {todos.map(todo => (
        <TodoItem key={todo.id} todo={todo} onToggle={onToggle} onUpdate={onUpdate} onDelete={onDelete} />
      ))}
    </div>
  );
}
