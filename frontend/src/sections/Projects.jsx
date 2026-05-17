import { motion } from 'framer-motion'
import { FiArrowUpRight, FiGithub } from 'react-icons/fi'
import SectionHeading from '../components/SectionHeading'
import { projects } from '../data/portfolioData'

export default function Projects() {
  return <section id="projects" className="mx-auto max-w-6xl px-6 py-20 md:px-12">
    <SectionHeading title="Projects" subtitle="Selected work" />
    <div className="grid gap-7 md:grid-cols-2 lg:grid-cols-3">{projects.map((project, index) => (
      <motion.article key={project.title} initial={{ opacity: 0, y: 25 }} whileInView={{ opacity: 1, y: 0 }} viewport={{ once: true }} transition={{ delay: index * 0.08 }} className="group overflow-hidden rounded-3xl border border-white/15 bg-white/5">
        <img src={project.image} alt={project.title} className="h-44 w-full object-cover transition duration-500 group-hover:scale-105"/>
        <div className="p-5"><h3 className="text-xl font-semibold">{project.title}</h3><p className="mt-2 text-slate-300">{project.description}</p>
        <p className="mt-3 text-xs uppercase tracking-wider text-cyan-300">{project.tech.join(' · ')}</p>
        <div className="mt-4 flex gap-4"><a href={project.github} className="inline-flex items-center gap-2 hover:text-cyan-200">Code <FiGithub /></a><a href={project.demo} className="inline-flex items-center gap-2 hover:text-cyan-200">Live <FiArrowUpRight /></a></div>
        </div></motion.article>
    ))}</div>
  </section>
}
