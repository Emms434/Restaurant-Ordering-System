import { useEffect, useState } from 'react'

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

export default function App() {
  const [menu, setMenu] = useState([])
  const [order, setOrder] = useState(null)

  useEffect(() => {
    fetch(`${API_BASE}/menu`).then(r => r.json()).then(setMenu)
  }, [])

  const createOrder = async () => {
    const response = await fetch(`${API_BASE}/orders`, { method: 'POST' })
    const payload = await response.json()
    setOrder(payload)
  }

  const addItem = async (itemName) => {
    if (!order) return
    const response = await fetch(`${API_BASE}/orders/${order.id}/items`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ itemName })
    })
    setOrder(await response.json())
  }

  return (
    <main>
      <h1>Restaurant Ordering</h1>
      <button onClick={createOrder}>{order ? `Order #${order.id}` : 'Create Order'}</button>
      <h2>Menu</h2>
      <ul>
        {menu.map(item => (
          <li key={item.id}>
            <strong>{item.name}</strong> - ${item.price}
            <button disabled={!order} onClick={() => addItem(item.name)}>Add</button>
          </li>
        ))}
      </ul>
      <h2>Order Summary</h2>
      {order ? (
        <>
          <ul>
            {order.lines.map(line => (
              <li key={line.itemName}>{line.itemName} x{line.quantity} (${line.lineTotal})</li>
            ))}
          </ul>
          <p>Total: ${order.total}</p>
        </>
      ) : <p>No order created yet.</p>}
    </main>
  )
}
