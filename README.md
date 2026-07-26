# Coding Assessment Platform

A scalable backend for an online coding assessment platform built using **Spring Boot**. The platform enables administrators to manage contests and problems, candidates to participate in coding contests, and provides a modular judging pipeline for evaluating submissions.

## Features

### Authentication & Authorization
- JWT-based authentication
- Role-Based Access Control (RBAC)
- Roles:
    - Admin
    - Problem Setter
    - Candidate

### Contest Management
- Create, update and publish contests
- Configure registration and contest windows
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
- Judge abstraction layer

### Leaderboard
- Highest submission per problem
- Total score calculation
- Solved problem count
- Dynamic ranking

### API Documentation
- Swagger / OpenAPI
- JWT Authorization support

---

# Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security 7 |
| Authentication | JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| DTO Mapping | MapStruct |
| Boilerplate Reduction | Lombok |
| Build Tool | Maven |
| API Docs | Swagger (OpenAPI) |

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
                   │
                   ▼
            Spring Data JPA
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
│   ├── impl
├── util
└── CodingAssessmentPlatformApplication.java
```

---

# API Documentation

After starting the application:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Running Locally

## Clone

```bash
git clone https://github.com/DivyakshChachan/coding-assessment-platform.git
```

```bash
cd coding-assessment-platform
```

---

## Configure Database

Create a PostgreSQL database.

```
coding_platform
```

Configure your local credentials in:

```
application-local.yml
```

---

## Start

```bash
mvn spring-boot:run
```

---

# Default Roles

| Role | Permissions |
|------|-------------|
| Admin | Manage contests and problems |
| Problem Setter | Create and manage problems |
| Candidate | Register, submit solutions and view leaderboard |

---

# Demo Workflow

1. Register users
2. Login
3. Create problems
4. Create contest
5. Publish contest
6. Add problems to contest
7. Register candidates
8. Submit solutions
9. View leaderboard

---

# Future Improvements

- Docker-based code execution
- Asynchronous judging
- Redis caching
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

B.Tech Artificial Intelligence  
National Institute of Technology Surat
