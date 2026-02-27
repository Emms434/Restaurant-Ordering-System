# Restaurant Ordering System

Monorepo with:
- `frontend/`: React + Vite SPA
- `ordering/`: Spring Boot + Maven REST API
- PostgreSQL persistence using Spring Data JPA + Flyway

## REST API (kept stable and simple)
- `GET /api/menu`
- `POST /api/orders`
- `GET /api/orders/{orderId}`
- `POST /api/orders/{orderId}/items` body: `{ "itemName": "Burger" }`
- `DELETE /api/orders/{orderId}/items` body: `{ "itemName": "Burger" }`

## Local run (Docker Compose)
```bash
docker compose up --build
```
Frontend: http://localhost:5173  
Backend: http://localhost:8080

## Backend-only local run
```bash
cd ordering
./mvnw spring-boot:run
```

## Frontend-only local run
```bash
cd frontend
npm install
npm run dev
```

## Migrations
Flyway SQL migrations live in:
- `ordering/src/main/resources/db/migration/V1__init_schema.sql`
- `ordering/src/main/resources/db/migration/V2__seed_menu.sql`

## Deployment
See [`docs/aws-architecture.md`](docs/aws-architecture.md) and GitHub Actions workflow at `.github/workflows/deploy.yml`.
