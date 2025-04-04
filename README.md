# Message Service

## Overview
The **Message Service** allows patients and medical staff to communicate via a secure messaging system.

## Features
- Secure messaging between users
- Role-based access (patients can only message their assigned doctors)
- Message history storage

## Technologies Used
- **Spring Boot**
- **JPA & Hibernate**
- **PostgreSQL/MySQL**
- **Docker & Kubernetes**

## Installation & Setup
```sh
git clone https://github.com/yourusername/message-service.git
cd message-service
mvn clean install
docker build -t message-service .
docker run -p 8085:8085 message-service
```

## Other Services
- Works with User Service for authentication.
