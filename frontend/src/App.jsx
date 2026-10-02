import { useEffect, useState } from 'react'

// Where the Spring Boot API lives. Vite injects VITE_API_BASE_URL at build
// time (set in docker-compose.yml locally and by the deploy workflow in
// production); the fallback is the local backend.
const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

// Small wrapper around fetch: sends JSON, parses the response, and turns
// non-2xx responses into thrown Errors carrying the API's {"error": "..."}
// message, so each caller can handle failures in one catch.
async function api(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    ...options,
    headers: { 'Content-Type': 'application/json', ...options.headers }
  })
  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.error || `Request failed (${response.status})`)
  }
  return payload
}

// Prices arrive as JSON numbers, which drop trailing zeros (14.50 -> 14.5),
// so always render them with exactly two decimal places.
const money = (value) => `$${Number(value).toFixed(2)}`

export default function App() {
  // menu: items from GET /menu. order: the current order returned by the API
  // (null until the user creates one). error: last failure message to show.
  const [menu, setMenu] = useState([])
  const [order, setOrder] = useState(null)
  const [error, setError] = useState(null)

  // Runs any API call, stores the order it returns, and shows errors inline.
  // Every order endpoint returns the full updated order, so the UI just
  // replaces its copy instead of recalculating totals itself.
  const run = async (request) => {
    try {
      setError(null)
      setOrder(await request())
    } catch (e) {
      setError(e.message)
    }
  }

  // Load the menu once when the page first renders.
  useEffect(() => {
    api('/menu').then(setMenu).catch(e => setError(`Could not load menu: ${e.message}`))
  }, [])

  const createOrder = () => run(() => api('/orders', { method: 'POST' }))

  const addItem = (itemName) => run(() =>
    api(`/orders/${order.id}/items`, { method: 'POST', body: JSON.stringify({ itemName }) }))

  const removeItem = (itemName) => run(() =>
    api(`/orders/${order.id}/items`, { method: 'DELETE', body: JSON.stringify({ itemName }) }))

  return (
    <main>
      <h1>Restaurant Ordering</h1>
      {error && <p className="error" role="alert">{error}</p>}

      {/* Step 1: start an order. Once created, the button shows its number. */}
      <button onClick={createOrder} disabled={!!order}>
        {order ? `Order #${order.id}` : 'Create Order'}
      </button>

      {/* Step 2: add dishes. "Add" is disabled until an order exists. */}
      <h2>Menu</h2>
      <ul>
        {menu.map(item => (
          <li key={item.id}>
            <strong>{item.name}</strong> - {money(item.price)}
            <button disabled={!order} onClick={() => addItem(item.name)}>Add</button>
          </li>
        ))}
      </ul>

      {/* Step 3: review. Totals come straight from the API response. */}
      <h2>Order Summary</h2>
      {order ? (
        <>
          {order.lines.length === 0 && <p>No items yet.</p>}
          <ul>
            {order.lines.map(line => (
              <li key={line.itemName}>
                {line.itemName} x{line.quantity} ({money(line.lineTotal)})
                <button onClick={() => removeItem(line.itemName)}>Remove</button>
              </li>
            ))}
          </ul>
          <p>Total: {money(order.total)}</p>
        </>
      ) : <p>No order created yet.</p>}
    </main>
  )
}
