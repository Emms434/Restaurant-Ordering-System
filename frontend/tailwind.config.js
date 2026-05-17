/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif']
      },
      boxShadow: {
        glow: '0 10px 60px -15px rgba(56, 189, 248, 0.45)'
      },
      backgroundImage: {
        'mesh-gradient': 'radial-gradient(circle at 20% 20%, rgba(56,189,248,0.25), transparent 35%), radial-gradient(circle at 80% 0%, rgba(168,85,247,0.22), transparent 40%), radial-gradient(circle at 70% 80%, rgba(34,197,94,0.18), transparent 42%)'
      }
    }
  },
  plugins: []
}
