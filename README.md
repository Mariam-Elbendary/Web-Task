E-Commerce Web Application (Java EE & Redis)

Overview :

This is a full-stack Java web application I developed to demonstrate a robust authentication system and optimized product management. The project focuses on security, performance through caching, and high availability.

Key Features I Implemented:

Role-Based Access Control (RBAC): Integrated a dual-role system (Admin/User). Admins have full CRUD privileges on products, while Users can view products and submit feedback.

Secure Authentication: Built a custom registration and login flow with email validation and secure session management.

JWT & Session Handling: Implemented JSON Web Tokens (JWT) combined with Redis for stateful session storage to ensure secure and fast user authorization.

Performance Optimization (Redis Caching): Leveraged Redis to cache product lists, significantly reducing database load and improving response times.

API Rate Limiting: Integrated a rate-limiting mechanism using Redis to protect the application from brute-force attacks and excessive API calls.

Database Management: Designed a MySQL schema to handle users, products, and user feedback with integrity constraints.
