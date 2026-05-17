import { motion } from 'framer-motion'

const links = ['home', 'about', 'skills', 'projects', 'contact']

export default function Navbar() {
  return <motion.nav initial={{ y: -40, opacity: 0 }} animate={{ y: 0, opacity: 1 }} className="fixed left-1/2 top-4 z-50 w-[min(92%,800px)] -translate-x-1/2 rounded-full border border-white/20 bg-slate-900/70 px-5 py-3 backdrop-blur-md">
    <ul className="flex justify-center gap-5 text-sm capitalize text-slate-200">{links.map((link) => <li key={link}><a href={`#${link}`} className="transition hover:text-cyan-300">{link}</a></li>)}</ul>
  </motion.nav>
}
