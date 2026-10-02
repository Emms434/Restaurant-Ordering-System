// Vite build/dev-server config. host 0.0.0.0 lets the dev server be reached
// from outside its Docker container on port 5173.
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0',
    port: 5173
  }
})
