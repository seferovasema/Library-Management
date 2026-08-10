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

### Caching
- Read-heavy Book endpoints (`findById`, `getAll`, `getBooksByAuthor`) cached with Spring Cache (Caffeine, in-memory)
- Automatic cache invalidation on create/update/delete
- Configurable TTL and max size (`cache.book.ttl-minutes`, `cache.book.max-size`)

### File Upload & Download
- Multipart file upload (JPG, JPEG, PNG, WEBP)
- File size limit and extension whitelist validation
- Real content-type verification via file signature (magic bytes) — rejects
  spoofed files (e.g. a `.txt` renamed to `.jpg`)

### Scheduled Tasks
- Daily cleanup of loan records older than 30 days (`@Scheduled`)

### Asynchronous Notifications
- Non-blocking email notification simulation on borrow/return (`@Async`)

### Additional Features
- DTO Pattern
- Global Exception Handling
- Validation
- Swagger/OpenAPI Documentation
- MapStruct Mapping
- EntityGraph Optimization
- Pagination & Sorting
- PostgreSQL Database
- Spring Cache Abstraction (Caffeine)
- Environment-based configuration (dev / prod profiles)

---

## 🛠️ Technologies

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Cache (Caffeine)
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
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
│   ├── impl
│   ├── notification
│   └── scheduled
└── specification
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
    - Upload files

- **USER**
    - View resources
    - Borrow books
    - Return books
    - Download files

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

The project uses Spring profiles (`dev` / `prod`), each with its own
`application-{profile}.yaml`. The default active profile is `dev`
(`spring.profiles.active`), overridable via the `SPRING_PROFILES_ACTIVE`
environment variable.

Set the following environment variables:

```properties
DB_USERNAME=postgres
DB_PASSWORD=your_password

JWT_SECRET=your_secret_key

ADMIN_USERNAME=admin
ADMIN_EMAIL=admin@library.com
ADMIN_PASSWORD=your_admin_password

# Optional overrides
FILE_UPLOAD_DIR=uploads
FILE_ALLOWED_EXTENSIONS=jpg,jpeg,png,webp
FILE_MAX_SIZE_BYTES=5242880
```

| Profile | ddl-auto | show-sql | Purpose                                  |
|---------|----------|----------|-------------------------------------------|
| `dev`   | update   | true     | Local development                        |
| `prod`  | validate | false    | Production (schema managed via migrations) |

application.yaml (common)

```yaml
spring:
  application:
    name: library-management
  profiles:
    active: dev

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000

admin:
  default-username: ${ADMIN_USERNAME}
  default-email: ${ADMIN_EMAIL}
  default-password: ${ADMIN_PASSWORD}

cache:
  book:
    ttl-minutes: 10
    max-size: 500

file:
  upload:
    dir: ${FILE_UPLOAD_DIR:uploads}
    allowed-extensions: ${FILE_ALLOWED_EXTENSIONS:jpg,jpeg,png,webp}
    max-size-bytes: ${FILE_MAX_SIZE_BYTES:5242880}
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

Run with a specific profile:

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
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
GET    /books
GET    /books/{id}
GET    /books/author/{authorId}
GET    /books/search
POST   /books
PUT    /books/{id}
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
GET  /loans
GET  /loans/overdue
```

### Files

```
POST /files/upload
GET  /files/{fileName}
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
- Controller-level (MockMvc) tests
- Redis Cache (alternative to in-memory Caffeine)
- CI/CD Pipeline

---

## 📄 License

This project is for educational purposes.
