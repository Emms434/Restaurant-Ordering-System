// Frontend entry point: mounts the <App /> component into the #root div in
// index.html. StrictMode adds extra development-only checks.
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './styles.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
