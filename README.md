# User Profile API

A comprehensive User Profile management API built with Spring Boot.

## Base URL
`http://localhost:8080/api/profiles`

## Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/profiles` | Create a new user profile |
| **GET** | `/api/profiles/{id}` | Retrieve a profile by ID |
| **GET** | `/api/profiles/search?username={name}` | Search profiles by username |
| **GET** | `/api/profiles/search?country={country}` | Search profiles by country |
| **GET** | `/api/profiles/search?minAge={min}&maxAge={max}` | Search profiles by age range |
| **PUT** | `/api/profiles/{id}` | Update an existing profile |
| **POST** | `/api/profiles/{id}/activate` | Activate a user profile |
| **POST** | `/api/profiles/{id}/deactivate` | Deactivate a user profile |
| **DELETE** | `/api/profiles/{id}` | Delete a user profile |

## Test Data & Usage Examples

### 1. Create a User Profile
**Endpoint:** `POST /api/profiles`

**Request Body:**
```json
{
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 30,
    "country": "USA",
    "bio": "Software Engineer",
    "active": true
}
```

**Response:**
```json
{
    "success": true,
    "message": "User profile created successfully",
    "data": {
        "userId": 1,
        "username": "john_doe",
        "email": "john@example.com",
        "fullName": "John Doe",
        "age": 30,
        "country": "USA",
        "bio": "Software Engineer",
        "active": true
    }
}
```

### 2. Get User Profile
**Endpoint:** `GET /api/profiles/1`

### 3. Search Profiles
**Endpoint:** `GET /api/profiles/search?username=john`

**Response:**
```json
{
    "success": true,
    "message": "Profiles found",
    "data": [
        {
            "userId": 1,
            "username": "john_doe",
            "email": "john@example.com",
            "fullName": "John Doe",
            "age": 30,
            "country": "USA",
            "bio": "Software Engineer",
            "active": true
        }
    ]
}
```

### 4. Update User Profile
**Endpoint:** `PUT /api/profiles/1`

**Request Body:**
```json
{
    "fullName": "John Jonathan Doe"
}
```

### 5. Deactivate User
**Endpoint:** `POST /api/profiles/1/deactivate`

**Response:**
```json
{
    "success": true,
    "message": "User deactivated successfully",
    "data": {
        "userId": 1,
        "username": "john_doe",
        "email": "john@example.com",
        "fullName": "John Jonathan Doe",
        "age": 30,
        "country": "USA",
        "bio": "Software Engineer",
        "active": false
    }
}
```

### 6. Delete User
**Endpoint:** `DELETE /api/profiles/1`
