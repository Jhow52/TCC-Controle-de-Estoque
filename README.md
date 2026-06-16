# 📦 Stock Management System

A complete inventory management system developed as a Final Course Project (TCC), focused on inventory control, product management, category organization, stock movement tracking, user authentication, and role-based access control.

## 🚀 About the Project

The goal of this project is to provide a secure and efficient inventory management solution for businesses that need to control products, stock levels, inventory movements, and user permissions.

The system was built following REST API best practices using Spring Boot, JWT authentication, role-based authorization, exception handling, unit testing, and clean architecture principles.

---

## 🛠️ Technologies Used

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* MySQL
* H2 Database (Testing)
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Lombok

### Frontend

* Angular
* TypeScript
* HTML
* CSS

### Tools

* Git
* GitHub
* IntelliJ IDEA
* VS Code
* Postman

---

# ✨ Features

## Authentication

* User registration
* User login
* JWT token generation
* Password encryption using BCrypt

## Authorization

* USER role
* ADMIN role
* Role-based endpoint protection
* Admin promotion
* Admin removal
* Protection against removing your own admin role
* Protection against removing the last system administrator

## Product Management

* Create product
* Update product
* Delete product
* Search product by ID
* Search product by name
* List all products

## Category Management

* Create category
* Update category
* Delete category
* Search category by ID
* Search category by name
* List all categories
* Duplicate category validation

## Inventory Control

* List inventory
* Search inventory by product
* Search inventory by category
* Search inventory by ID
* Low stock monitoring

## Stock Movement

* Register stock entry
* Register stock exit
* Track movement history
* Search movements by product

## Exception Handling

* Global exception handling
* Standardized error responses
* Validation errors
* Business rule exceptions

## Testing

* Repository Tests
* Service Layer Tests
* H2 In-Memory Database
* Mockito Mocks

---

# 🔒 Security

The application uses JWT (JSON Web Token) authentication and Spring Security for authorization.

Protected endpoints require a valid JWT token.

Example Authorization Header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# 📂 Project Structure

```text
src
├── controller
├── service
│   ├── impl
├── repository
├── model
├── dto
├── security
├── handler
├── configuration
└── util
```

---

# 📖 API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# ⚙️ Running the Project

## Clone Repository

```bash
git clone https://github.com/your-username/stock-management-system.git
```

## Navigate to Project

```bash
cd stock-management-system
```

## Configure Database

Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/stock
spring.datasource.username=root
spring.datasource.password=your_password
```

## Run Application

```bash
./gradlew bootRun
```

or

```bash
mvn spring-boot:run
```

---

# 🧪 Running Tests

```bash
./gradlew test
```

or

```bash
mvn test
```

---

# 📸 Screenshots

## Login

(Add screenshot here)

## Dashboard

(Add screenshot here)

## Product Management

(Add screenshot here)

## Category Management

(Add screenshot here)

## Inventory Control

(Add screenshot here)

## Swagger Documentation

(Add screenshot here)

---

# 🎯 Business Rules Implemented

* Email must be unique.
* Category names cannot be duplicated.
* Stock quantity cannot become negative.
* Users cannot remove their own ADMIN role.
* The last ADMIN user cannot be removed.
* Categories associated with products cannot be deleted.

---

# 📚 What I Learned

During the development of this project, I improved my knowledge in:

* Object-Oriented Programming
* REST APIs
* Spring Boot
* Spring Security
* JWT Authentication
* Exception Handling
* Unit Testing
* Repository Testing
* Database Modeling
* Angular Integration
* Clean Code Principles
* Git and GitHub

---

# 👨‍💻 Author

Developed by Jhonata.

This project was created as a Final Course Project (TCC) and portfolio project to demonstrate backend and full-stack development skills using Java and Spring Boot.
