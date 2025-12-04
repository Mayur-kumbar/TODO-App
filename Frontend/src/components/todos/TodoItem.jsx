import React, { useState } from "react";

export default function TodoItem({ todo, onToggle, onUpdate, onDelete }){
  const [editing, setEditing] = useState(false);
  const [text, setText] = useState(todo.title);

  const save = () => {
    onUpdate(todo.id, { title: text });
    setEditing(false);
  };

  return (
    <li className="bg-white p-3 rounded shadow flex items-center justify-between">
      <div className="flex items-center gap-3">
        <input type="checkbox" checked={todo.done} onChange={()=>onToggle(todo.id)} />
        <div>
          {editing ? (
            <input value={text} onChange={e=>setText(e.target.value)} className="border rounded p-1" />
          ) : (
            <div className={`font-medium ${todo.done ? "line-through text-slate-400" : ""}`}>{todo.title}</div>
          )}
          {todo.dueDate && <div className="text-xs text-slate-500">Due: {todo.dueDate}</div>}
        </div>
      </div>
      <div className="flex items-center gap-2">
        {editing ? (
          <button onClick={save} className="text-sm px-2 py-1 border rounded">Save</button>
        ) : (
          <button onClick={()=>setEditing(true)} className="text-sm px-2 py-1 border rounded">Edit</button>
        )}
        <button onClick={()=>onDelete(todo.id)} className="text-sm px-2 py-1 border rounded text-red-600">Delete</button>
      </div>
    </li>
  );
}
