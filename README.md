# Spring Help Desk

A role-based help desk web application built with **Spring Boot**, **Thymeleaf**, **Spring Security**, **Spring Data JPA**, **Flyway**, and **H2**.

This project was built to simulate a real internal support system where users can register, log in, create support tickets, and track their status, while agents can review, assign, and manage incoming tickets through a separate dashboard.

## Features

### Authentication & Users
- User registration with validation
- Secure login with Spring Security
- Password hashing with BCrypt
- Role-based access control
- Roles supported:
  - `REQUESTER`
  - `AGENT`
  - `ADMIN`

### Requester Workflow
- Create new support tickets
- View all personal tickets
- Open individual ticket details
- Close tickets
- Reopen resolved or closed tickets
- View ticket metadata such as priority, status, created time, and updated time

### Agent Workflow
- View all submitted tickets
- Filter tickets by status and priority
- Open detailed agent-side ticket views
- Assign tickets to yourself
- Update ticket status as an agent

### Technical Features
- Server-side validation using Jakarta Validation
- MVC structure with controllers, services, repositories, and domain models
- Database schema managed with Flyway migrations
- H2 in-memory database for local development
- Thymeleaf templates with reusable navbar fragment
- Health check endpoint at `/api/health`

## Tech Stack

- Java 17
- Spring Boot 3
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- Flyway
- H2 Database
- Maven

## Project Structure

```text
src/main/java/com/victorpena/helpdesk
├── controller
│   ├── AccountController
│   ├── AgentController
│   ├── AuthController
│   ├── HealthController
│   ├── HomeController
│   └── TicketController
├── domain
│   ├── Ticket
│   ├── TicketPriority
│   ├── TicketStatus
│   ├── User
│   └── UserRole
├── repo
│   ├── TicketRepository
│   └── UserRepository
├── security
│   ├── CustomUserDetailsService
│   └── SecurityConfig
├── service
│   ├── AuthService
│   └── TicketService
└── web
    ├── CreateTicketRequest
    └── RegisterRequest

src/main/resources
├── db/migration
│   └── V1__init.sql
├── static/css
├── templates
└── application.properties# spring-help-desk
