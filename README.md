# Blog Management System (BMS)

A **production-style Blog Management System** built with **Spring Boot**, featuring **JWT authentication**, **role-based access control (RBAC)**, **Swagger API documentation**, **email notifications**, and **logging**.

This project is designed with **real-world backend architecture practices** and is suitable for **intermediate-level backend developers**.

---

## 🚀 Features

* 🔐 JWT Authentication (Login & Signup)
* 👤 Role-Based Access Control (USER / ADMIN)
* 📝 Create, Update, Delete Blog Posts
* 💬 Comment on Posts
* ✉️ Email Notification on New Comment
* 📄 Swagger/OpenAPI Documentation
* 🧾 Global Exception Handling
* 📊 SLF4J Logging
* 🧪 Unit & Controller Tests (JUnit + Mockito)

---

## 🛠 Tech Stack

| Layer         | Technology                  |
| ------------- | --------------------------- |
| Backend       | Spring Boot 3               |
| Security      | Spring Security + JWT       |
| ORM           | Spring Data JPA             |
| Database      | MySQL                       |
| Documentation | Swagger (springdoc-openapi) |
| Email         | JavaMailSender (Gmail SMTP) |
| Logging       | SLF4J + Logback             |
| Testing       | JUnit 5, Mockito, MockMvc   |

---

## 📦 Project Structure (High Level)

```
com.siteshkumar.bms
├── Config        # Security & Swagger configs
├── Controller    # REST Controllers
├── DTO           # Request & Response DTOs
├── Entity        # JPA Entities
├── Repository    # JPA Repositories
├── Security      # JWT, Filters, UserDetails
├── Service       # Business Logic
├── Mapper        # Entity ↔ DTO Mappers
├── Error         # Global Exception Handling
└── TestData      # Test Data Factories
```

---

## 🔐 Authentication Flow

1. User signs up → password stored using **BCrypt**
2. User logs in → receives **JWT token**
3. JWT is sent in `Authorization` header
4. `JwtAuthFilter` validates token
5. User info loaded into `SecurityContext`

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 📌 API Base URL

```
http://localhost:8080/api/v1
```

---

## 🔑 Auth APIs

| Method | Endpoint       | Description       | Public |
| ------ | -------------- | ----------------- | ------ |
| POST   | `/auth/signup` | Register new user | ✅      |
| POST   | `/auth/login`  | Login & get JWT   | ✅      |

### Signup Request

```json
{
  "username": "john",
  "email": "john@gmail.com",
  "password": "password123"
}
```

### Login Response

```json
{
  "id": 1,
  "username": "john",
  "email": "john@gmail.com",
  "token": "<JWT_TOKEN>"
}
```

---

## 📝 Post APIs

| Method | Endpoint                 | Description    | Role          |
| ------ | ------------------------ | -------------- | ------------- |
| POST   | `/posts/create`          | Create post    | USER          |
| PUT    | `/posts/update/{postId}` | Update post    | OWNER         |
| DELETE | `/posts/delete/{postId}` | Delete post    | OWNER / ADMIN |
| GET    | `/posts/public/all`      | Get all posts  | Public        |
| GET    | `/posts/public/{postId}` | Get post by ID | Public        |

### Create Post

```json
{
  "title": "Spring Security",
  "content": "JWT explained"
}
```

---

## 💬 Comment APIs

| Method | Endpoint                              | Description    | Role          |
| ------ | ------------------------------------- | -------------- | ------------- |
| POST   | `/posts/comments/add/{postId}`        | Add comment    | USER          |
| GET    | `/posts/comments/public/all/{postId}` | Get comments   | Public        |
| DELETE | `/posts/comments/delete/{commentId}`  | Delete comment | OWNER / ADMIN |

### Add Comment

```json
{
  "text": "Great post!"
}
```

---

## ✉️ Email Notification

* Triggered when a **new comment** is added
* Email sent to **post author**
* Uses **Gmail SMTP with App Password**

---

## 🧪 Testing

### Implemented Tests

* ✅ Service Layer Tests (Mockito)
* ✅ Controller Tests (WebMvcTest)
* ✅ Security filters disabled for controller tests

### Run Tests

```bash
mvn test
```

---

## 📄 Swagger Documentation

Swagger UI available at:

```
http://localhost:8080/api/v1/swagger-ui.html
```

### JWT in Swagger

1. Click **Authorize**
2. Enter:

```
Bearer <JWT_TOKEN>
```

---

## ⚙️ Application Properties (Important)

```properties
spring.application.name=bms

spring.datasource.url=your_database_url
spring.datasource.username=your_user_name
spring.datasource.password=your_password

#DB-Configuration
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

#api-version
server.servlet.context-path=/api/v1

#Spring security config
spring.security.user.name=your-user-name
spring.security.user.password=your-user-password

#JWT secret key
jwt.secretKey=your-secret-key

#Spring Mail Set-up
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email-address
spring.mail.password=your-app-password

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🔒 Security Highlights

* Stateless authentication
* Method-level security with `@PreAuthorize`
* Only owners can update/delete their content
* Admin can delete any post/comment

---

## Load Testing (Apache JMeter) Pictures
<img width="1920" height="1080" alt="Screenshot (57)" src="https://github.com/user-attachments/assets/816ce4fe-65ce-43f5-a102-67e9d10681a2" />
<img width="1920" height="1080" alt="Screenshot (56)" src="https://github.com/user-attachments/assets/1c73e3c9-6073-4f01-8693-b50594b8b34e" />
<img width="1920" height="1080" alt="Screenshot (55)" src="https://github.com/user-attachments/assets/4ce55435-47f1-4973-8e28-5c6956d1def1" />
<img width="1920" height="1080" alt="Screenshot (54)" src="https://github.com/user-attachments/assets/070ad010-dba2-4979-a8da-b9380453df9c" />
<img width="1920" height="1080" alt="Screenshot (64)" src="https://github.com/user-attachments/assets/74f6f57f-2832-4849-89e2-aa3a5f1e9f64" />
<img width="1920" height="1080" alt="Screenshot (62)" src="https://github.com/user-attachments/assets/2b4e6313-ef1e-4768-befe-8a96f58c8709" />
<img width="1920" height="1080" alt="Screenshot (61)" src="https://github.com/user-attachments/assets/1852e27e-68c2-4cf8-962e-352ca73833ab" />
<img width="1920" height="1080" alt="Screenshot (60)" src="https://github.com/user-attachments/assets/714022a6-7601-495c-aae9-1abe696211c9" />
<img width="1920" height="1080" alt="Screenshot (59)" src="https://github.com/user-attachments/assets/7126e2eb-00c7-4d6e-a047-5ffc8139a184" />
<img width="1920" height="1080" alt="Screenshot (58)" src="https://github.com/user-attachments/assets/01ba043d-b168-4b68-9821-94eb7170a382" />

## 📌 Future Enhancements

* Refresh Token support
* Pagination & Sorting
* User profile management
* Dockerization
* CI/CD pipeline

---

## 👨‍💻 Author

**Sitesh Kumar**
Software Development Engineer | Java | Spring Boot |

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and feel free to fork & contribute!
