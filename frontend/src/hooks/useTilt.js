import { useState } from 'react'

export function useTilt(limit = 10) {
  const [style, setStyle] = useState({ transform: 'perspective(900px) rotateX(0deg) rotateY(0deg)' })

  const onMove = (event) => {
    const rect = event.currentTarget.getBoundingClientRect()
    const x = (event.clientX - rect.left) / rect.width
    const y = (event.clientY - rect.top) / rect.height
    const rotateX = (0.5 - y) * limit
    const rotateY = (x - 0.5) * limit
    setStyle({ transform: `perspective(900px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)` })
  }

  const onLeave = () => setStyle({ transform: 'perspective(900px) rotateX(0deg) rotateY(0deg)' })

  return { style, onMove, onLeave }
}
