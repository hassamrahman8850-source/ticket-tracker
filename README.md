# Ticket Tracker

A support ticket tracker built with Java, Spring Boot and PostgreSQL. It exposes a REST API for creating, viewing, updating and deleting tickets, with input validation and clear error responses.

## Tech stack

- Java 21, Spring Boot 3.5, Spring Data JPA (Hibernate)
- PostgreSQL 16 (run in Docker)
- Maven

## API

| Method | Endpoint | Description | Success | Errors |
| --- | --- | --- | --- | --- |
| POST | `/api/tickets` | Create a ticket | 201 | 400 |
| GET | `/api/tickets` | List tickets, newest first | 200 | |
| GET | `/api/tickets/{id}` | Get one ticket | 200 | 404 |
| PUT | `/api/tickets/{id}` | Update a ticket | 200 | 400, 404 |
| DELETE | `/api/tickets/{id}` | Delete a ticket | 204 | 404 |

Example request:

```json
POST /api/tickets
{ "title": "Printer not working", "description": "Room 204 printer shows offline" }
```

Validation errors return a clear 400 response:

```json
{ "status": 400, "error": "Validation failed", "fields": { "title": "title is required" } }
```

## Run it locally

You need JDK 21, Maven and Docker Desktop.

1. Start the database from the project root:

   ```powershell
   docker compose up -d
   ```

   The database listens on port 5433 so it does not clash with a PostgreSQL installed on the default port 5432.

2. Start the API:

   ```powershell
   cd backend
   mvn spring-boot:run
   ```

3. Try it:

   ```powershell
   Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/tickets -ContentType "application/json" -Body '{"title":"Printer not working","description":"Room 204 printer shows offline"}'
   Invoke-RestMethod http://localhost:8080/api/tickets
   ```

To stop: press `Ctrl + C` in the API window, then `docker compose down`.

## Project structure

```
backend/src/main/java/com/hassam/tickets/
  TicketTrackerApplication.java    app entry point
  ticket/
    TicketController.java          HTTP endpoints
    TicketService.java             business logic
    TicketRepository.java          database access (Spring Data JPA)
    Ticket.java                    entity mapped to the tickets table
    CreateTicketRequest.java       request body for create, with validation rules
    UpdateTicketRequest.java       request body for update, with validation rules
    TicketResponse.java            response body
    ApiExceptionHandler.java       turns errors into clean JSON (400, 404)
```

A request flows Controller, then Service, then Repository, then PostgreSQL.

## Planned

- JWT login and protected endpoints (Spring Security)
- Unit tests (JUnit, Mockito) and API tests
- React and TypeScript front end
- Dockerfile for the app and a full docker-compose setup
- GitHub Actions pipeline that builds and runs the tests on every push