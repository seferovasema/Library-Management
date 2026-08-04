# 📚 Library Management System

A RESTful Library Management System built with **Spring Boot** that allows managing books, authors, categories, members, loans, and users. The project follows a layered architecture and implements authentication and authorization using **Spring Security** and **JWT**.

---

## 🚀 Features

### Authentication & Authorization
- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization (ADMIN, USER)
- BCrypt Password Encryption

### Book Management
- Create Book
- Update Book
- Delete Book
- Get All Books (Pagination & Sorting)
- Get Book By ID
- Search Books
- Assign Author
- Assign Categories

### Author Management
- CRUD Operations
- View Author's Books

### Category Management
- CRUD Operations
- Many-to-Many Relationship with Books

### Member Management
- CRUD Operations
- View Borrowed Books

### Loan Management
- Borrow Book
- Return Book
- Prevent Borrowing Already Borrowed Books
- Get Active Loans
- Get Overdue Loans

### Additional Features
- DTO Pattern
- Global Exception Handling
- Validation
- Swagger/OpenAPI Documentation
- MapStruct Mapping
- EntityGraph Optimization
- Pagination & Sorting
- PostgreSQL Database

---

## 🛠️ Technologies

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- MapStruct
- Lombok
- Gradle
- Swagger / OpenAPI

---

## 📂 Project Structure

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
│   └── interfaces
└── util
```

---

## 🗄️ Database Schema

### Entities

- User
- Author
- Book
- Category
- Member
- Loan

### Relationships

- Author → OneToMany → Book
- Book → ManyToOne → Author
- Book → ManyToMany → Category
- Member → OneToMany → Loan
- Book → OneToMany → Loan

---

## 🔐 Security

Authentication is implemented using **JWT**.

Roles:

- **ADMIN**
    - Full CRUD access
    - Manage users
    - Manage books
    - Manage authors
    - Manage categories
    - Manage members
    - Manage loans

- **USER**
    - View resources
    - Borrow books
    - Return books

---

## 📖 API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI:

```
http://localhost:8080/v3/api-docs
```

---

## ⚙️ Configuration

Create an `.env` or configure environment variables:

```properties
DB_USERNAME=postgres
DB_PASSWORD=your_password

JWT_SECRET=your_secret_key
```

application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/library_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

---

## ▶️ Running the Project

### Clone Repository

```bash
git clone https://github.com/seferovasema/Library-Management.git
```

### Enter Project

```bash
cd Library-Management
```

### Run

Windows

```bash
gradlew bootRun
```

Linux / Mac

```bash
./gradlew bootRun
```

---

## 📌 Example Endpoints

### Authentication

```
POST /auth/register
POST /auth/login
```

### Books

```
GET /books
GET /books/{id}
POST /books
PUT /books/{id}
DELETE /books/{id}
```

### Authors

```
GET /authors
POST /authors
PUT /authors/{id}
DELETE /authors/{id}
```

### Categories

```
GET /categories
POST /categories
PUT /categories/{id}
DELETE /categories/{id}
```

### Members

```
GET /members
POST /members
PUT /members/{id}
DELETE /members/{id}
```

### Loans

```
POST /loans/borrow
POST /loans/return
GET /loans
GET /loans/overdue
```

---

## 🧪 Validation

The project uses:

- Jakarta Validation
- Global Exception Handler
- Custom Exceptions

Examples:

- ResourceNotFoundException
- UserAlreadyExistsException
- EmailAlreadyExistsException

---

## 📷 Screenshots

You can add screenshots here.

Example:

```
images/
    swagger.png
    database.png
```

---

## 👩‍💻 Author

**Sema Seferova**

Java Backend Developer

GitHub:
https://github.com/seferovasema

---

## ⭐ Future Improvements

- Docker Support
- Unit Tests (JUnit & Mockito)
- Integration Tests
- Redis Cache
- Email Notifications
- Audit Logging
- CI/CD Pipeline

---

## 📄 License

This project is for educational purposes.
