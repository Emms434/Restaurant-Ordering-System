import { motion } from 'framer-motion'
import { roles } from '../data/portfolioData'

export default function Hero() {
  return (
    <section id="home" className="relative overflow-hidden px-6 pb-20 pt-28 md:px-12">
      <div className="absolute inset-0 bg-mesh-gradient opacity-80" />
      <div className="relative mx-auto grid max-w-6xl items-center gap-10 md:grid-cols-2">
        <motion.div initial={{ opacity: 0, y: 18 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.8 }}>
          <p className="mb-4 text-cyan-300">Hello, I’m Alex.</p>
          <h1 className="text-5xl font-bold leading-tight md:text-6xl">I build delightful digital products.</h1>
          <motion.p key={roles[0]} className="mt-5 text-xl text-slate-300" animate={{ opacity: [0.4, 1, 0.4] }} transition={{ duration: 4, repeat: Infinity }}>
            {roles.join(' • ')}
          </motion.p>
          <div className="mt-8 flex gap-4">
            <a href="#projects" className="rounded-full bg-white px-6 py-3 font-medium text-slate-900 transition hover:scale-105">See Projects</a>
            <a href="#contact" className="rounded-full border border-white/40 px-6 py-3 transition hover:bg-white/10">Let’s Talk</a>
          </div>
        </motion.div>
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.9 }}
          className="relative h-80 rounded-[2.5rem] border border-white/20 bg-white/10 p-6 shadow-glow backdrop-blur-md"
        >
          <div className="h-full rounded-[2rem] bg-gradient-to-br from-cyan-300/30 via-violet-400/30 to-emerald-400/25" />
        </motion.div>
      </div>
    </section>
  )
}
