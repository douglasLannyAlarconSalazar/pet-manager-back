# Auth Service API Documentation

## Overview
This is the Authentication and Authorization Service for the Pet Manager System. It handles user authentication, JWT token generation and validation, user management, and role-based access control.

## API Documentation with Swagger/OpenAPI

The API is fully documented using Swagger/OpenAPI 3.0. This provides an interactive interface to explore and test all available endpoints.

### Accessing the Documentation

Once the service is running, you can access the API documentation at:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

### Features

The Swagger documentation includes:
- **Complete endpoint descriptions** with request/response examples
- **Schema definitions** for all DTOs
- **Authentication testing** with JWT Bearer tokens
- **Try it out functionality** to test endpoints directly from the browser
- **Response codes and error descriptions**

## Available Endpoints

### Authentication Endpoints (`/api/auth`)

#### 1. Login
- **POST** `/api/auth/login`
- **Description**: Authenticate user with username/email and password
- **Request Body**:
```json
{
  "usernameOrEmail": "admin",
  "password": "Admin@123"
}
```
- **Response**:
```json
{
  "accessToken": "eyJhbGciOiJSUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJSUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

#### 2. Refresh Token
- **POST** `/api/auth/refresh`
- **Description**: Generate a new access token using a valid refresh token
- **Request Body**:
```json
{
  "refreshToken": "eyJhbGciOiJSUzI1NiJ9..."
}
```

### User Management Endpoints (`/api/users`)

All user management endpoints require authentication. Admin-only endpoints are marked below.

#### 1. Create User (Admin Only)
- **POST** `/api/users`
- **Description**: Create a new user in the system
- **Request Body**:
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "SecurePass123",
  "roleId": 2,
  "enabled": true
}
```

#### 2. Get All Users (Admin Only)
- **GET** `/api/users`
- **Description**: Retrieve a list of all users

#### 3. Get User by ID
- **GET** `/api/users/{id}`
- **Description**: Get user information by ID
- **Access**: Admin or the user themselves

#### 4. Get User by Username
- **GET** `/api/users/username/{username}`
- **Description**: Get user information by username
- **Access**: Admin or the user themselves

#### 5. Update User
- **PUT** `/api/users/{id}`
- **Description**: Update user information
- **Access**: Admin or the user themselves

#### 6. Delete User (Admin Only)
- **DELETE** `/api/users/{id}`
- **Description**: Delete a user from the system

#### 7. Enable User (Admin Only)
- **PATCH** `/api/users/{id}/enable`
- **Description**: Enable a disabled user account

#### 8. Disable User (Admin Only)
- **PATCH** `/api/users/{id}/disable`
- **Description**: Disable a user account

### Public Key Endpoint (`/auth`)

#### Get JWT Public Key
- **GET** `/auth/public-key`
- **Description**: Retrieve the public key for JWT token verification
- **Use Case**: Other microservices can use this endpoint to validate JWT tokens

### Test Endpoints (`/api/hello`)

#### 1. Public Endpoint
- **GET** `/api/hello/public`
- **Description**: Test endpoint that doesn't require authentication

#### 2. Private Endpoint
- **GET** `/api/hello/private`
- **Description**: Test endpoint that requires authentication
- **Authentication**: Required

#### 3. Get Current User
- **GET** `/api/hello/me`
- **Description**: Get detailed information about the currently authenticated user
- **Authentication**: Required

## Authentication

The API uses JWT (JSON Web Tokens) for authentication with RS256 algorithm.

### How to Authenticate in Swagger UI

1. First, call the `/api/auth/login` endpoint with valid credentials
2. Copy the `accessToken` from the response
3. Click the **"Authorize"** button at the top of the Swagger UI
4. Enter the token in the format: `Bearer {your-token-here}`
5. Click **"Authorize"**
6. Now you can test all protected endpoints

### Default Admin Credentials

For development/testing:
- **Username**: `admin`
- **Email**: `admin@petmanager.com`
- **Password**: `Admin@123`

## Role-Based Access Control

The system has two main roles:
- **ADMIN**: Full access to all endpoints
- **USER**: Limited access (can only manage their own information)

## Configuration

### Swagger Configuration

The Swagger configuration is located in `OpenApiConfig.java` and includes:
- API information (title, version, description)
- Server URLs (local and production)
- Security scheme definition (Bearer JWT)

### Application Properties

Swagger-related configuration in `application.properties`:
```properties
# Springdoc OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

## Response Codes

- **200 OK**: Request successful
- **201 Created**: Resource created successfully
- **204 No Content**: Request successful with no content to return
- **400 Bad Request**: Invalid input data
- **401 Unauthorized**: Authentication required or invalid token
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource already exists

## Data Models

### AuthRequest
```json
{
  "usernameOrEmail": "string",
  "password": "string"
}
```

### AuthResponse
```json
{
  "accessToken": "string",
  "refreshToken": "string",
  "tokenType": "string"
}
```

### UserRequest
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "roleId": "integer",
  "enabled": "boolean"
}
```

### UserResponse
```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "roleName": "string",
  "roleId": "integer",
  "enabled": "boolean",
  "createdAt": "date",
  "lastAccess": "date"
}
```

## Development

### Dependencies

The project uses SpringDoc OpenAPI for API documentation:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

### Building the Project

```bash
# Compile the project
./mvnw clean compile

# Run tests
./mvnw test

# Package the application
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

### Running with Docker

```bash
# Build the Docker image
docker build -t auth-service .

# Run the container
docker run -p 8080:8080 auth-service
```

## Support

For issues or questions, please contact the Pet Manager team at support@petmanager.com

## License

Apache 2.0

