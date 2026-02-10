# Product API Project

This project implements a RESTful API for managing products. It allows users to perform CRUD operations (Create, Read, Update, Delete) on product data, featuring search, filtering, and pagination capabilities.

## Base URL

`http://localhost:8080/api/products`

## API Endpoints

| HTTP Method | Endpoint | Description | Query Parameters / Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/` | Get all products | `page` (default: 0), `limit` (default: 5) |
| **GET** | `/{productId}` | Get product by ID | - |
| **GET** | `/category/{category}` | Get products by category | - |
| **GET** | `/brand/{brand}` | Get products by brand | - |
| **GET** | `/search` | Search products by keyword | `keyword` (required) |
| **GET** | `/price-range` | Get products within price range | `min`, `max` (required) |
| **GET** |`/in-stock` | Get all in-stock products | - |
| **POST** | `/` | Add a new product | JSON Request Body (see below) |
| **PUT** | `/{productId}` | Update an existing product | JSON Request Body (see below) |
| **PATCH** | `/{productId}/stock` | Update product stock quantity | `quantity` (required) |
| **DELETE** | `/{productId}` | Delete a product | - |

## Test Data

You can use the following JSON data to test the `POST` and `PUT` methods.

### 1. Add New Product (POST)

**URL:** `http://localhost:8080/api/products`

**Body (JSON):**

```json
{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse with long battery life",
  "price": 29.99,
  "category": "Electronics",
  "stockQuantity": 50,
  "brand": "Logitech"
}
```

### 2. Update Product (PUT)

**URL:** `http://localhost:8080/api/products/{productId}` (Replace `{productId}` with actual ID, e.g., `1`)

**Body (JSON):**

```json
{
  "name": "iPhone 14 Pro",
  "description": "Apple smartphone with advanced camera system",
  "price": 1299.0,
  "category": "Electronics",
  "stockQuantity": 10,
  "brand": "Apple"
}
```

### 3. Update Stock Only (PATCH)

**URL:** `http://localhost:8080/api/products/{productId}/stock?quantity=100`

## How to Run

1.  Ensure you have Java and Maven installed.
2.  Clone the repository.
3.  Navigate to the project directory.
4.  Run the application using the following command:

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api/products`.
