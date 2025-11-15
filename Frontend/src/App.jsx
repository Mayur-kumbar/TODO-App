import axios from "axios"
import { useEffect } from "react"


function App() {

  useEffect(()=>{
    axios.get("http://localhost:8080/api/todos")
    .then(res => console.log(res.data))
  })

  return (
    <>
      <div className="bg-red-300">This is Frontend</div>
    </>
  )
}

export default App
