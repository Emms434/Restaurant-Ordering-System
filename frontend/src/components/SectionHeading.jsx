import { motion } from 'framer-motion'

export default function SectionHeading({ title, subtitle }) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      whileInView={{ opacity: 1, y: 0 }}
      viewport={{ once: true, amount: 0.4 }}
      transition={{ duration: 0.55 }}
      className="mb-8"
    >
      <p className="mb-2 text-sm uppercase tracking-[0.35em] text-cyan-300">{subtitle}</p>
      <h2 className="text-3xl font-semibold text-white md:text-4xl">{title}</h2>
    </motion.div>
  )
}
