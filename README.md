# Restaurant Ordering System

A full-stack restaurant ordering app in a single repository.

## Project structure

- `frontend/` — React + Vite single-page application (customer order UI).
- `ordering/` — Spring Boot REST API (menu + order management).
- `docker-compose.yml` — local multi-container setup (frontend, backend, postgres).
- `docs/aws-architecture.md` — high-level deployment architecture notes.

## Features

- View menu items.
- Create an order.
- Add and remove items from an order.
- Fetch an order by ID.
- Persist data in PostgreSQL.
- Database schema + seed data managed with Flyway migrations.

## API endpoints

Base URL: `http://localhost:8080/api`

- `GET /menu`
- `POST /orders`
- `GET /orders/{orderId}`
- `POST /orders/{orderId}/items` with JSON body:
  ```json
  { "itemName": "Burger" }
  ```
- `DELETE /orders/{orderId}/items` with JSON body:
  ```json
  { "itemName": "Burger" }
  ```

## Prerequisites

For local non-Docker setup:

- Java 17+ (project compiles for Java 17)
- Maven 3.9+
- Node.js 18+
- npm 9+
- PostgreSQL 16 (or Docker to run postgres)

For Docker setup:

- Docker + Docker Compose plugin

## Quick start (recommended: Docker)

From repo root:

```bash
docker compose up --build
```

Services:

- Frontend: <http://localhost:5173>
- Backend: <http://localhost:8080>
- Postgres: `localhost:5432`

To stop:

```bash
docker compose down
```

To stop and remove DB volume:

```bash
docker compose down -v
```

## Run without Docker

### 1) Start PostgreSQL

Create database/user if needed:

- DB: `restaurant`
- User: `restaurant`
- Password: `restaurant`

### 2) Run backend

```bash
cd ordering
./mvnw spring-boot:run
```

If you do not want to use the Maven wrapper:

```bash
cd ordering
mvn spring-boot:run
```

### 3) Run frontend

```bash
cd frontend
npm install
npm run dev
```

## Build commands

Backend package build:

```bash
cd ordering
./mvnw clean package
```

Frontend production build:

```bash
cd frontend
npm run build
```

## Why your backend build may fail

A common failure in this project is Maven being unable to download dependencies or the parent POM from Maven Central (for example, HTTP `403 Forbidden` or wrapper download failures). This typically looks like:

- `Non-resolvable parent POM ... spring-boot-starter-parent ... status code: 403`
- `wget: Failed to fetch ... apache-maven-...-bin.zip`

### Root cause

The backend depends on external Maven artifacts from:

- `https://repo.maven.apache.org/maven2`

If your machine or network blocks that host (corporate proxy, firewall, DNS filtering, or temporary upstream issue), backend builds fail before compilation starts.

### Fix options

1. Ensure Maven Central is reachable from your environment.
2. Configure Maven proxy settings in `~/.m2/settings.xml`.
3. If your company uses an internal artifact mirror (Nexus/Artifactory), configure it as a Maven mirror in `settings.xml`.
4. Retry with system Maven (`mvn ...`) if wrapper bootstrap is blocked.
5. In CI or restricted environments, pre-cache dependencies or use an allowed internal registry.

## Flyway migrations

- `ordering/src/main/resources/db/migration/V1__init_schema.sql`
- `ordering/src/main/resources/db/migration/V2__seed_menu.sql`

## Deployment

See:

- `docs/aws-architecture.md`
- `.github/workflows/deploy.yml`
