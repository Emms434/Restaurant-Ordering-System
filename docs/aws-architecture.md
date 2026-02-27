# AWS target architecture

## Components
- **Frontend**: React/Vite static bundle hosted in **S3**, fronted by **CloudFront**.
- **Backend**: Spring Boot JAR on **Elastic Beanstalk** (single environment) with environment variables.
- **Database**: **Amazon RDS PostgreSQL** with security group access restricted to Elastic Beanstalk instances.

## Request flow
1. User hits CloudFront URL.
2. CloudFront serves SPA assets from S3.
3. Frontend calls backend API over HTTPS (EB CNAME/custom domain).
4. Backend reads/writes orders + menu in RDS Postgres.

## SPA routing fix
In CloudFront behavior/custom error responses:
- HTTP 403 -> `/index.html` with response code 200
- HTTP 404 -> `/index.html` with response code 200

## Backend environment variables (EB)
- `SPRING_DATASOURCE_URL=jdbc:postgresql://<rds-endpoint>:5432/restaurant`
- `SPRING_DATASOURCE_USERNAME=<db_user>`
- `SPRING_DATASOURCE_PASSWORD=<db_password>`
- `APP_CORS_ALLOWED_ORIGIN=https://<cloudfront-domain-or-custom-domain>`

## Local development stack
- Docker Postgres 16
- Spring Boot backend container
- Vite dev server container
