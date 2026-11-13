# Random Number - Maven Multi-Module Spring Boot Project

This repository contains two independently runnable Spring Boot applications in one Maven parent project.

- **Microservice A** exposes `GET /api/random` on port `8081` and calls Microservice B over HTTP.
- **Microservice B** exposes `POST /api/random` on port `8082`, generates a number from 1 to 1,000,000, saves it in PostgreSQL, and stores it in Redis.
- PostgreSQL and Redis are started locally with Docker Compose.

## Requirements

- JDK 21
- Maven 3.6.3+
- Docker Desktop (for PostgreSQL and Redis)

The project uses Spring Boot 3.5.7. You can change the version in the root `pom.xml` if needed.

## Start PostgreSQL and Redis

From the project root:

```bash
docker compose up -d
```

## Build both applications

```bash
mvn clean package
```

## Run Microservice B first

In terminal 1:

```bash
mvn -pl microservice-b spring-boot:run
```

## Run Microservice A

In terminal 2:

```bash
mvn -pl microservice-a spring-boot:run
```

## Test the flow

Call Microservice A:

```bash
curl http://localhost:8081/api/random
```

Example response (the ID, number, and timestamp will differ):

```json
{
  "id": 1,
  "value": 482913,
  "generatedAt": "2026-11-13T10:00:00Z"
}
```

A calls B using HTTP POST. B creates the number, writes it to PostgreSQL and Redis, then returns the response through A.

You can also call B directly:

```bash
curl -X POST http://localhost:8082/api/random
```

Health checks:

- http://localhost:8081/actuator/health
- http://localhost:8082/actuator/health

## Verify storage

PostgreSQL table: `random_numbers`

Redis key format: `random:number:<database-id>`

For example, to inspect Redis:

```bash
docker exec -it random-number-redis redis-cli
GET random:number:1
```

To inspect PostgreSQL:

```bash
docker exec -it random-number-postgres psql -U postgres -d randomdb
SELECT * FROM random_numbers;
```

## Notes

- The sample uses local-development credentials in `docker-compose.yml`; do not use these credentials in production.
- The sample writes to PostgreSQL and Redis in sequence. They do not participate in one distributed atomic transaction. If Redis is unavailable after the database write, the database row may already have been committed or may commit depending on transaction timing. Production systems should add retry/reconciliation or an outbox-style strategy if both stores must eventually agree.
- The endpoint uses HTTP POST in B because generating and storing a new number changes server state.
