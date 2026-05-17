import { socials } from '../data/portfolioData'
import SectionHeading from '../components/SectionHeading'

export default function Contact() {
  return <section id="contact" className="mx-auto max-w-6xl px-6 py-20 md:px-12">
    <SectionHeading title="Contact" subtitle="Let’s build" />
    <div className="grid gap-8 md:grid-cols-2">
      <form className="space-y-4 rounded-3xl border border-white/15 bg-white/5 p-6">
        <input className="w-full rounded-xl border border-white/15 bg-white/10 p-3" placeholder="Name" />
        <input className="w-full rounded-xl border border-white/15 bg-white/10 p-3" placeholder="Email" />
        <textarea rows="5" className="w-full rounded-xl border border-white/15 bg-white/10 p-3" placeholder="Tell me about your project" />
        <button type="button" className="rounded-full bg-cyan-300 px-6 py-3 font-medium text-slate-900 transition hover:scale-105">Send Message</button>
      </form>
      <div className="rounded-3xl border border-white/15 bg-white/5 p-6">
        <p className="text-slate-300">Open to freelance and full-time opportunities.</p>
        <div className="mt-6 flex gap-4">{socials.map(({ label, href, icon: Icon }) => <a key={label} href={href} className="rounded-full border border-white/20 p-3 transition hover:-translate-y-1 hover:bg-white/10"><Icon /></a>)}</div>
      </div>
    </div>
  </section>
}
