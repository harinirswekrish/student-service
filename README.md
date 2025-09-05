# 🎓 Student Service – Mini School ERP

## 📌 Overview
The **Student Service** is a microservice in the **Mini School ERP System**.  
It is responsible for managing student records and enforces **role-based access control**.
- Authentication & authorization via **JWT**  
- Token caching/blacklisting with **Redis**  

- **Admin** → Can create and view students  
- **Staff** → Can only view students  

---

## ⚙️ Tech Stack
- Java 8+
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Swagger (OpenAPI)
- Spring Security (JWT)

---

## 🏗️ Architecture & Flow
1. **API Gateway** validates JWT and routes requests.  
2. Requests reach **Student Service** running on `8082`.  
3. Student Service interacts with **PostgreSQL** for persistence.  
4. Responses are returned in **JSON** with proper HTTP status codes.  

---## 🏗️ Authentication Flow
1. **Login** via `/auth/login` with username & password.  
2. On success → JWT is generated and stored in **Redis**.  
3. JWT must be sent in `Authorization: Bearer <token>` header for all secured APIs.  
4. Token expiry, token is invalidated in Redis.

## 🗄️ Database Schema
**Table: students**

| Column      | Type         | Constraints      |
| ----------- | ------------ | ---------------- |
| id          | BIGSERIAL PK | Primary Key      |
| first\_name | VARCHAR(100) | NOT NULL         |
| last\_name  | VARCHAR(100) | NOT NULL         |
| email       | VARCHAR(150) | UNIQUE, NOT NULL |
| dob         | DATE         |                  |
| created\_at | TIMESTAMP    | DEFAULT now()    |
| updated\_at | TIMESTAMP    | ON UPDATE now()  |

**Table: users**

| Column      | Type         | Constraints                  |
| ----------- | ------------ | ---------------------------- |
| id          | BIGSERIAL PK | Primary Key                  |
| username    | VARCHAR(50)  | UNIQUE, NOT NULL             |
| password    | VARCHAR(255) | NOT NULL (BCrypt encoded)    |
| role        | VARCHAR(20)  | NOT NULL (`ADMIN` / `STAFF`) |
| created\_at | TIMESTAMP    | DEFAULT now()                |


---

## 🚀 Setup & Run

### Prerequisites
- JDK 8+  
- Maven 3+  
- PostgreSQL running locally  

### Steps
```bash
# Clone repository
git clone https://github.com/harinirswekrish/student-service.git
cd student-service

# Update application.properties with DB credentials
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=yourpassword

# Build project
mvn clean install

# Run service
mvn spring-boot:run
