import { motion } from 'framer-motion'
import SectionHeading from '../components/SectionHeading'
import { timeline } from '../data/portfolioData'

export default function Timeline() {
  return <section className="mx-auto max-w-6xl px-6 py-20 md:px-12">
    <SectionHeading title="Experience" subtitle="Journey" />
    <div className="space-y-6 border-l border-white/20 pl-6">{timeline.map((item, index) => (
      <motion.div key={item.year} initial={{ opacity: 0, x: -15 }} whileInView={{ opacity: 1, x: 0 }} viewport={{ once: true }} transition={{ delay: index * 0.1 }} className="relative rounded-2xl border border-white/15 bg-white/5 p-5">
        <span className="absolute -left-[33px] top-7 h-3 w-3 rounded-full bg-cyan-300" />
        <p className="text-sm text-cyan-300">{item.year} · {item.place}</p><h3 className="mt-1 text-lg font-semibold">{item.title}</h3><p className="mt-2 text-slate-300">{item.summary}</p>
      </motion.div>
    ))}</div>
  </section>
}
