# Library Management System

A RESTful Library Management System developed with Spring Boot.

## Features

- Author, Book and Member management
- CRUD operations
- Layered Architecture (Controller → Service → Repository)
- DTO and MapStruct
- Bean Validation
- Global Exception Handling
- Pagination and Sorting
- Swagger / OpenAPI Documentation
- Unit Testing with JUnit 5 and Mockito

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle
- Lombok
- MapStruct
- Swagger (OpenAPI)
- JUnit 5
- Mockito

## Project Structure

```
Controller
    ↓
Service
    ↓
Repository
```

## API Endpoints

### Authors
- POST /authors
- GET /authors
- GET /authors/{id}
- PUT /authors/{id}
- DELETE /authors/{id}

### Books
- POST /books
- GET /books
- GET /books/{id}
- PUT /books/{id}
- DELETE /books/{id}

### Members
- POST /members
- GET /members
- GET /members/{id}
- PUT /members/{id}
- DELETE /members/{id}

## Project Status

✅ Week 1 Completed

Implemented:
- Layered Architecture
- CRUD APIs
- DTO Mapping
- Validation
- Exception Handling
- Pagination & Sorting
- Swagger Documentation
- Unit Tests

## Author

**Sema Seferova**
