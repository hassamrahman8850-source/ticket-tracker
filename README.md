# Ticket Tracker

A support ticket tracker: Java + Spring Boot REST API, PostgreSQL, and (later) a React/TypeScript front end.

## Day 1: run the API

### What you need installed (Windows)

1. **JDK 21** - check with `java -version`. If missing: `winget install EclipseAdoptium.Temurin.21.JDK`
2. **Maven** - check with `mvn -v`. If missing: `winget install Apache.Maven` (or just use IntelliJ, which has Maven built in)
3. **Docker Desktop** - check with `docker --version`. If missing: `winget install Docker.DockerDesktop`, then open it once and wait until it says "Engine running"

Close and reopen PowerShell after installing anything.

### Step 1 - start the database

From the project root (the folder with `docker-compose.yml`):

```powershell
docker compose up -d
docker ps
```

You should see a container named `tickets-db` with status "Up".

### Step 2 - start the API

```powershell
cd backend
mvn spring-boot:run
```

The first run downloads dependencies and takes a few minutes. It is ready when you see:

```
Started TicketTrackerApplication in ... seconds
```

Leave this window open.

### Step 3 - create a ticket

Open a second PowerShell window:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/tickets `
  -ContentType "application/json" `
  -Body '{"title":"Printer not working","description":"Room 204 printer shows offline"}'
```

You should get back the ticket with `id` 1 and `status` OPEN.

### Step 4 - list tickets

```powershell
Invoke-RestMethod http://localhost:8080/api/tickets
```

Or open http://localhost:8080/api/tickets in your browser.

### Stopping

- API: `Ctrl + C` in its window
- Database: `docker compose down` (your data is kept; add `-v` to wipe it)

## If something goes wrong

| What you see | Likely cause |
| --- | --- |
| `Connection to localhost:5432 refused` | Database is not running. Run `docker compose up -d` |
| `password authentication failed for user "tickets"` | Another PostgreSQL is already using port 5432. Stop it, or change the port to `"5433:5432"` in `docker-compose.yml` and `5433` in `application.properties` |
| `Port 8080 was already in use` | Add `server.port=8081` to `application.properties` |
| `release version 21 not supported` | `java -version` is showing an older JDK |

## How the code is organised

```
backend/src/main/java/com/hassam/tickets/
  TicketTrackerApplication.java   starts the app
  ticket/
    Ticket.java                   the database table as a Java class
    TicketStatus.java             OPEN / IN_PROGRESS / CLOSED
    TicketRepository.java         database access (Spring writes the SQL)
    TicketService.java            business logic
    TicketController.java         the HTTP endpoints
    CreateTicketRequest.java      JSON coming in
    TicketResponse.java           JSON going out
```

A request flows: Controller -> Service -> Repository -> PostgreSQL.
