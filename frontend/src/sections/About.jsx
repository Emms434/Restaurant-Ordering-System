import { motion } from 'framer-motion'
import SectionHeading from '../components/SectionHeading'

export default function About() {
  return (
    <section id="about" className="mx-auto max-w-6xl px-6 py-20 md:px-12">
      <SectionHeading title="About Me" subtitle="Who I am" />
      <motion.p initial={{ opacity: 0, y: 20 }} whileInView={{ opacity: 1, y: 0 }} viewport={{ once: true }} className="max-w-3xl text-lg leading-relaxed text-slate-300">
        I’m a frontend engineer passionate about turning product ideas into immersive and intuitive experiences. I care deeply about motion, usability, and craft—building interfaces that feel as good as they look.
      </motion.p>
    </section>
  )
}
