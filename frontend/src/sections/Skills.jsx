import { motion } from 'framer-motion'
import SectionHeading from '../components/SectionHeading'
import { skills } from '../data/portfolioData'
import { useTilt } from '../hooks/useTilt'

function SkillCard({ skill }) {
  const tilt = useTilt(8)
  return <div onMouseMove={tilt.onMove} onMouseLeave={tilt.onLeave} style={tilt.style} className="rounded-2xl border border-white/20 bg-white/5 p-5 transition duration-200">
    <div className={`mb-3 h-2 rounded-full bg-gradient-to-r ${skill.color}`} />
    <h3 className="text-lg font-semibold">{skill.name}</h3><p className="text-slate-300">{skill.level}</p>
  </div>
}

export default function Skills() {
  return <section id="skills" className="mx-auto max-w-6xl px-6 py-20 md:px-12">
    <SectionHeading title="Skills" subtitle="Toolkit" />
    <motion.div initial={{ opacity: 0 }} whileInView={{ opacity: 1 }} viewport={{ once: true }} className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
      {skills.map((skill) => <SkillCard key={skill.name} skill={skill} />)}
    </motion.div>
  </section>
}
