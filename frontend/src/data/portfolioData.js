import { FiGithub, FiLinkedin, FiMail } from 'react-icons/fi'

export const roles = ['Frontend Engineer', 'UI Motion Enthusiast', 'Product-minded Developer']

export const skills = [
  { name: 'React', level: 'Advanced', color: 'from-cyan-400 to-blue-500' },
  { name: 'TypeScript', level: 'Strong', color: 'from-indigo-400 to-violet-500' },
  { name: 'Framer Motion', level: 'Strong', color: 'from-fuchsia-400 to-pink-500' },
  { name: 'Tailwind CSS', level: 'Advanced', color: 'from-emerald-400 to-cyan-500' },
  { name: 'Node.js', level: 'Intermediate', color: 'from-lime-400 to-green-500' },
  { name: 'UX Prototyping', level: 'Strong', color: 'from-orange-400 to-rose-500' }
]

export const projects = [
  { title: 'Pulse Analytics', description: 'SaaS dashboard with real-time visual storytelling for startup teams.', tech: ['React', 'D3', 'Node'], github: '#', demo: '#', image: 'https://images.unsplash.com/photo-1551281044-8d8d7f4f5c47?auto=format&fit=crop&w=900&q=80' },
  { title: 'Flow Commerce', description: 'Shopping experience focused on motion and conversion-friendly interactions.', tech: ['Vite', 'Framer Motion', 'Stripe'], github: '#', demo: '#', image: 'https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=900&q=80' },
  { title: 'Nimbus Notes', description: 'Collaborative notes app with playful micro-interactions and sync.', tech: ['React', 'Firebase', 'Tailwind'], github: '#', demo: '#', image: 'https://images.unsplash.com/photo-1455390582262-044cdead277a?auto=format&fit=crop&w=900&q=80' }
]

export const timeline = [
  { year: '2025', title: 'Senior Frontend Engineer', place: 'Bright Labs', summary: 'Led frontend rebuild improving performance and experience polish.' },
  { year: '2023', title: 'Product Engineer', place: 'Nova Studio', summary: 'Built responsive marketing experiences and design system components.' },
  { year: '2021', title: 'Frontend Developer', place: 'Freelance', summary: 'Delivered websites and apps for early-stage companies.' }
]

export const socials = [
  { label: 'GitHub', href: '#', icon: FiGithub },
  { label: 'LinkedIn', href: '#', icon: FiLinkedin },
  { label: 'Email', href: 'mailto:hello@example.com', icon: FiMail }
]
