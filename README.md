# 📚 Library Management System

A RESTful Library Management System built with **Spring Boot** that allows managing books, authors, members, and users. The project follows a layered architecture and includes authentication and authorization using **Spring Security** and **JWT**.

## 🚀 Features

* User Registration & Login
* User Authentication & Authorization
* JWT Authentication
* Role-Based Authorization (ADMIN, USER)
* BCrypt Password Encryption
* CRUD operations for Books
* CRUD operations for Authors
* CRUD operations for Members
* DTO Pattern
* MapStruct Mapping
* Bean Validation
* Request Validation
* Global Exception Handling
* Custom Authentication Entry Point
* Custom Access Denied Handler
* Pagination & Sorting
* Swagger/OpenAPI Documentation
* PostgreSQL Database
* Layered Architecture (Controller → Service → Repository)

---

## 🛠 Technologies

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* PostgreSQL
* Hibernate
* Lombok
* MapStruct
* Bean Validation
* Swagger/OpenAPI
* Gradle

---

## 📂 Project Structure

```text
src
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
│   └── impl
└── resources
```

---

## 🔐 Authentication

The application uses JWT-based authentication.

### Public Endpoints

```http
POST /auth/register
POST /auth/login
```

After a successful login, the application returns a JWT token.

Protected endpoints require a valid JWT token in the `Authorization` header:

```http
Authorization: Bearer <your_jwt_token>
```

---

## 📖 API Endpoints

### Authentication

```http
POST /auth/register
POST /auth/login
```

### Authors

```http
GET    /authors
GET    /authors/{id}
POST   /authors
PUT    /authors/{id}
DELETE /authors/{id}
```

### Books

```http
GET    /books
GET    /books/{id}
POST   /books
PUT    /books/{id}
DELETE /books/{id}
```

### Members

```http
GET    /members
GET    /members/{id}
POST   /members
PUT    /members/{id}
DELETE /members/{id}
```

---

## 📑 API Documentation

Swagger UI is available after starting the application:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄 Database

**Database:** PostgreSQL

The application uses Spring Data JPA and Hibernate for database operations.

---

## ▶ Running the Project

Clone the repository:

```bash
git clone https://github.com/seferovasema/Library-Management.git
```

Go to the project directory:

```bash
cd Library-Management
```

Run the application:

**Linux / macOS**

```bash
./gradlew bootRun
```

**Windows**

```bash
gradlew.bat bootRun
```

You can also run the project directly from IntelliJ IDEA.

---

## 👩‍💻 Author

**Sema Seferova**

GitHub Repository: https://github.com/seferovasema/Library-Management
