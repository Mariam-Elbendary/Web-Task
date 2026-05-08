<h1 align="center">Java Backend Architecture</h1>

<p align="center">
  JSP / Servlets · JWT · Sessions · Redis · MySQL · Docker
</p>

<p align="center">
  <img src="https://github.com/MohamedRadi20/Hot_dog/blob/882fc32cd7e619dc39400617bf56dd041fd85db4/arch.png" width="750">
</p>

---

## Overview

This project demonstrates how modern backend systems handle authentication, state management, caching, and persistent data storage using industry-relevant patterns.

It shows how a Java web application can combine stateless authentication using JWTs with distributed session management, while integrating relational databases and in-memory infrastructure commonly found in production systems.

---

## Architecture

The system is composed of four main layers:

- **Application Layer (JSP / Servlets)**  
  Handles HTTP requests, business logic, authentication flows, and communication with external services.

- **Authentication Layer (Filter + JWT + Sessions)**  
  A custom servlet filter intercepts incoming requests before they reach business logic.  
  It validates JWT tokens, checks authentication state, and verifies active sessions.

- **MySQL (Relational Database)**  
  Stores persistent application data including user accounts, credentials, and business entities.

- **Redis (Distributed Session Store)**  
  Stores shared session state, authentication sessions, cached data, and rate limiting counters.

Redis runs inside Docker to simulate production-like infrastructure.

---

## Authentication Flow

### Request Filtering

Every incoming request passes through a custom authentication filter before reaching the application.

The filter is responsible for:

- Intercepting protected requests
- Extracting JWT tokens from headers or cookies
- Validating token integrity and expiration
- Checking active session state in Redis
- Allowing or rejecting access based on authentication status

This mirrors how security middleware works in real backend systems.

---

### Login Flow

When a user logs in:

1. Credentials are submitted
2. User data is verified against MySQL
3. A JWT is generated
4. A session is created and stored in Redis
5. The client receives authentication information

This demonstrates a hybrid authentication model:

- **JWT → identity and claims**
- **Redis Session → centralized session control**

This allows:

- Session invalidation
- Forced logout
- Multi-instance scalability
- Distributed authentication state

---

### Signup Flow

When a new user registers:

1. User data is validated
2. Password is securely stored
3. User account is inserted into MySQL
4. Authentication can be issued immediately after registration

A dedicated **Users** table is used to simulate real account management.

---

## Key Concepts

### JWT Authentication

JSON Web Tokens are used to carry authenticated identity information between client and server without storing user identity in server memory.

Benefits:

- Stateless identity verification
- Scalable across multiple instances
- Common in microservices and APIs

---

### Session Management

Redis is used as a shared session store.

Benefits:

- Sessions survive application restarts
- Multiple application servers can share authentication state
- Enables centralized logout and session expiration

---

### Caching

Redis is also used to cache frequently accessed data, reducing database load and improving performance.

---

### Rate Limiting

A rate limiting mechanism is implemented using Redis to control request volume and protect the system from abuse.

---

## Database Design

### Users Table

MySQL contains a users table responsible for storing:

- User ID
- Username
- Email
- Password Hash
- Account Metadata

This simulates real authentication systems.

---

## Tech Stack

- Java (JSP / Servlets)  
- Servlet Filters  
- JWT Authentication  
- Session Management  
- Apache Tomcat  
- MySQL  
- Redis  
- Docker  
- JDBC  

---

## Running the Project

1. Start Redis using Docker:

```bash
docker run -p 6379:6379 redis
```
2. Ensure MySQL is running on port 3306
3. Create the required database schema including the users table
4. Configure:
- Database connection
- Redis connection
- JWT secret key
5. Deploy the application on Tomcat
  
### Notes

This project focuses on core backend engineering concepts including:

- HTTP request interception
- Authentication middleware
- JWT token generation and validation
- Distributed session storage
- User account persistence
- Caching and rate limiting

The goal is to demonstrate how modern backend systems combine stateless authentication with centralized session management using production-inspired architecture.
