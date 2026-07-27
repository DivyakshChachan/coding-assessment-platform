# Coding Assessment Platform

A scalable backend for an online coding assessment platform built using **Spring Boot**. The platform enables administrators to manage contests and problems, candidates to participate in coding contests, and provides a modular judging pipeline for evaluating submissions.

---

# Features

### Authentication & Authorization
- JWT-based authentication
- Role-Based Access Control (RBAC)
- Roles:
  - Admin
  - Problem Setter
  - Candidate

### Contest Management
- Create, update, publish and manage contests
- Registration and contest window validation
- Public contest support

### Problem Management
- CRUD operations for coding problems
- Difficulty levels
- Time and memory limits
- Sample inputs and outputs

### Test Case Management
- Multiple test cases per problem
- Hidden and sample test cases

### Contest Registration
- Candidate registration
- Registration window validation

### Submission Pipeline
- Source code submission
- Multiple programming language support
- Attempt tracking
- Modular judge abstraction layer

### Leaderboard
- Highest submission per problem
- Total score calculation
- Solved problem count
- Dynamic ranking

### Performance & Reliability
- Redis-based caching
- Optimistic locking for concurrent updates

### API Documentation
- Swagger / OpenAPI
- JWT Authorization support

---

# Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Security | Spring Security 7 |
| Authentication | JWT |
| Database | PostgreSQL |
| Cache | Redis |
| ORM | Spring Data JPA / Hibernate |
| DTO Mapping | MapStruct |
| Boilerplate Reduction | Lombok |
| Build Tool | Maven |
| API Docs | Swagger (OpenAPI) |
| Containerization | Docker & Docker Compose |

---

# Architecture

```
                    Client
                       │
                       ▼
               REST Controllers
                       │
                       ▼
                   Services
              ┌────────┴────────┐
              ▼                 ▼
      Spring Data JPA      Redis Cache
              │
              ▼
         PostgreSQL
```

Project follows a layered architecture:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

---

# Project Structure

```
src
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
│   └── impl
├── util
└── CodingAssessmentPlatformApplication.java
```

---

# Running with Docker (Recommended)

## Clone the Repository

```bash
git clone https://github.com/DivyakshChachan/coding-assessment-platform.git

cd coding-assessment-platform
```

## Start the Application

```bash
docker compose up
```

This starts:

- Spring Boot application
- PostgreSQL
- Redis

Once the containers are running, access:

```
http://localhost:8080/swagger-ui/index.html
```

To stop the application:

```bash
docker compose down
```

---

# Running Locally

## Prerequisites

- Java 21
- Maven
- PostgreSQL
- Redis

Start PostgreSQL and Redis (or run only those services using Docker):

```bash
docker compose up postgres redis
```

Configure your local credentials in:

```
application-local.yml
```

Run the application:

```bash
mvn spring-boot:run
```

> **Note:** If the Docker application container is already running, stop it first to free port `8080`:
>
> ```bash
> docker compose stop app
> ```

---

# API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Default Roles

| Role | Permissions |
|------|-------------|
| Admin | Manage contests and problems |
| Problem Setter | Create and manage problems |
| Candidate | Register for contests, submit solutions and view leaderboards |

---

# Demo Workflow

1. Register users
2. Login
3. Create problems
4. Create a contest
5. Publish the contest
6. Add problems to the contest
7. Register candidates
8. Submit solutions
9. View the leaderboard

---

# Future Improvements

- Asynchronous judging
- Docker image publishing
- GitHub Actions CI/CD
- Unit and integration tests
- WebSocket live leaderboard
- Plagiarism detection
- Email notifications
- Contest editorials
- Virtual contests

---

# License

This project is licensed under the MIT License.

---

# Author

**Divyaksh Chachan**

B.Tech in Artificial Intelligence  
National Institute of Technology Surat
