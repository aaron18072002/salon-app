# ✂️ Salon Booking Platform

A complete, production-grade Salon Booking System built with a scalable Microservices Architecture. This platform provides a seamless booking experience for customers, a comprehensive management dashboard for salon owners, and a centralized admin panel, replicating the design patterns used by top-tier tech companies.

---

## 🔥 Key Features

*   **Customer Booking Flow:** Search for salons, filter services, and book appointments in real-time.
*   **Salon Owner Dashboard:** Dedicated portal to manage services, schedules, and incoming bookings.
*   **Admin Panel:** Centralized management for platform operations and monitoring.
*   **Online Payments:** Integrated with **Stripe** and **Razorpay** for secure, seamless checkout.
*   **Real-Time Notifications:** Powered by **WebSockets** for instant booking updates and alerts.
*   **Reviews & Ratings:** Built-in system for customer feedback and salon reputation management.
*   **Secure Authentication:** Centralized and highly secure identity management using **OAuth2** and **Keycloak**.

---

## 🧠 Architecture Overview

This project is built as a scalable distributed system utilizing **9 Independent Microservices**, designed to handle high traffic and ensure high availability. 

*   **API Gateway:** Single entry point for all client requests.
*   **Service Discovery:** Managed by **Netflix Eureka** for dynamic routing.
*   **Asynchronous Communication:** Event-driven architecture powered by **RabbitMQ**.
*   **Centralized Security:** API protection via **JWT**, **OAuth2**, and **Keycloak**.

---

## 🧰 Tech Stack

### Backend
*   **Frameworks:** Spring Boot, Spring Cloud
*   **Persistence:** Spring Data JPA, Hibernate, MySQL
*   **Messaging & Events:** RabbitMQ
*   **Service Mesh & Routing:** Eureka, OpenFeign
*   **Security:** Keycloak, JWT, OAuth2
*   **Real-time:** WebSockets
*   **Payments:** Stripe, Razorpay

### Frontend
*   **Library:** React
*   **State Management:** Redux Toolkit
*   **Styling:** Tailwind CSS
*   **Network:** Axios

### DevOps & Deployment
*   **Containerization:** Docker
*   **Orchestration:** Docker Compose

---

## 🎯 Project Goals & Learnings

This repository serves as a showcase of advanced backend development and system design principles, including:
*   Designing and decoupling real-world microservices.
*   Securing distributed applications with OAuth2 & Keycloak.
*   Implementing resilient async communication with RabbitMQ message brokers.
*   Building scalable, RESTful APIs following industry standards.
*   Containerizing and deploying complex environments using Docker.

---

## 🚀 Getting Started

*(Add instructions here on how to run the project locally. For example:)*

### Prerequisites
*   [Docker](https://www.docker.com/) & Docker Compose installed
*   Java 17+
*   Node.js
