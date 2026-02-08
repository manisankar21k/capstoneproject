# Insurance Policy Management System - Backend

A Spring Boot REST API for managing insurance policies, customers, claims, and payments.

## Project Structure

```
src/main/java/com/example/insurance_policy_management_system/
├── controller/          # REST Controllers
├── service/             # Business Logic
├── entity/              # JPA Entities
├── repository/          # Data Access
└── dto/                 # Data Transfer Objects
```

## Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## Setup Instructions

### 1. Database Setup

Create MySQL database:
```sql
CREATE DATABASE IF NOT EXISTS insurance_db;
USE insurance_db;
```

### 2. Update Database Credentials

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build Project

```bash
mvn clean install
```

### 4. Run Application

```bash
mvn spring-boot:run
```

Or using Maven wrapper:
```bash
./mvnw spring-boot:run
```

The server will start at `http://localhost:8080`

## API Endpoints

### Authentication

#### Login
- **URL:** `POST /api/auth/login`
- **Request:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```
- **Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "success": true,
    "message": "Login successful",
    "username": "admin",
    "email": "admin@insurance.com"
  }
}
```

#### Register
- **URL:** `POST /api/auth/register`
- **Request:**
```json
{
  "username": "newuser",
  "password": "password123",
  "email": "user@insurance.com",
  "fullName": "John Doe"
}
```

### Customer Management

#### Create Customer
- **URL:** `POST /api/customers`
- **Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "address": "123 Main St"
}
```

#### Get All Customers
- **URL:** `GET /api/customers`

#### Get Customer by ID
- **URL:** `GET /api/customers/{id}`

#### Update Customer
- **URL:** `PUT /api/customers/{id}`

#### Delete Customer
- **URL:** `DELETE /api/customers/{id}`

### Policy Management

#### Create Policy
- **URL:** `POST /api/policies`
- **Request:**
```json
{
  "policyName": "Term Life Insurance",
  "policyType": "Life",
  "premiumAmount": 5000,
  "durationMonths": 12,
  "coverageAmount": 1000000
}
```

#### Get All Policies
- **URL:** `GET /api/policies`

#### Get Policy by ID
- **URL:** `GET /api/policies/{id}`

#### Update Policy
- **URL:** `PUT /api/policies/{id}`

#### Delete Policy
- **URL:** `DELETE /api/policies/{id}`

### Policy Assignment (Customer-Policy Mapping)

#### Assign Policy to Customer
- **URL:** `POST /api/customer-policies`
- **Request:**
```json
{
  "customer": {
    "customerId": 1
  },
  "policy": {
    "policyId": 1
  },
  "policyStatus": "ACTIVE"
}
```

#### Get All Assignments
- **URL:** `GET /api/customer-policies`

#### Update Assignment Status
- **URL:** `PATCH /api/customer-policies/{id}/status?status=ACTIVE`

#### Delete Assignment
- **URL:** `DELETE /api/customer-policies/{id}`

### Payment Management

#### Record Payment
- **URL:** `POST /api/payments`
- **Request:**
```json
{
  "customerPolicy": {
    "customerPolicyId": 1
  },
  "amount": 5000,
  "paymentMode": "Online",
  "paymentStatus": "SUCCESS"
}
```

#### Get All Payments
- **URL:** `GET /api/payments`

#### Get Payment by ID
- **URL:** `GET /api/payments/{id}`

#### Update Payment Status
- **URL:** `PATCH /api/payments/{id}/status?status=SUCCESS`

#### Delete Payment
- **URL:** `DELETE /api/payments/{id}`

### Claim Management

#### File Claim
- **URL:** `POST /api/claims`
- **Request:**
```json
{
  "customerPolicy": {
    "customerPolicyId": 1
  },
  "claimAmount": 50000,
  "claimStatus": "PENDING",
  "description": "Claim for medical expenses"
}
```

#### Get All Claims
- **URL:** `GET /api/claims`

#### Get Claim by ID
- **URL:** `GET /api/claims/{id}`

#### Update Claim Status
- **URL:** `PATCH /api/claims/{id}/status?status=APPROVED`

#### Delete Claim
- **URL:** `DELETE /api/claims/{id}`

## Default Credentials

For testing purposes, you can use:
- **Username:** admin
- **Password:** admin123

## Database Schema

### tables
- `users` - User accounts for authentication
- `customers` - Customer information
- `policies` - Insurance policies
- `customer_policies` - Mapping between customers and policies
- `payments` - Payment records
- `claims` - Insurance claims

## Technologies Used

- Spring Boot 3.2.0
- Spring Data JPA
- MySQL
- Java 17
- Maven

## CORS Configuration

The API is configured to accept requests from:
- `http://localhost:4200` (Angular Frontend)
- `http://localhost:3000` (React Frontend)

## Error Handling

All endpoints return StandardResponse format:

**Success Response:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {}
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Error message here",
  "data": null
}
```

## Status Codes

- `200 OK` - Successful GET, PUT request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication failed
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate resource
- `500 Internal Server Error` - Server error

## License

MIT License
