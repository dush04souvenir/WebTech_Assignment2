# Simple Task Management API

This is a Spring Boot application for managing tasks. It provides a RESTful API to create, read, update, and delete tasks.

## Base URL

`http://localhost:8080/api/tasks`

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/` | Retrieve a list of all tasks. |
| **GET** | `/{taskId}` | Retrieve a specific task by its ID. |
| **GET** | `/status?completed={true/false}` | Retrieve tasks based on their completion status. |
| **GET** | `/priority/{priority}` | Retrieve tasks based on their priority (LOW, MEDIUM, HIGH). |
| **POST** | `/` | Create a new task. |
| **PUT** | `/{taskId}` | Update an existing task. |
| **PATCH** | `/{taskId}/complete` | Mark a specific task as completed. |
| **DELETE** | `/{taskId}` | Delete a specific task by its ID. |

## Testing POST Method

The following data can be used to test the creation of new tasks using the `POST` method.

| Scenario | Request Body (JSON) | Expected Result |
| :--- | :--- | :--- |
| **Create High Priority Task** | `{"taskId": 101, "title": "Critical Bug Fix", "description": "Fix login crash", "completed": false, "priority": "HIGH", "dueDate": "2026-03-31"}` | Task created successfully with ID 101. |
| **Create Medium Priority Task** | `{"taskId": 102, "title": "Write Documentation", "description": "Update API docs", "completed": false, "priority": "MEDIUM", "dueDate": "2026-04-10"}` | Task created successfully with ID 102. |
| **Create Completed Task** | `{"taskId": 103, "title": "Setup Environment", "description": "Install Java and Maven", "completed": true, "priority": "LOW", "dueDate": "2026-02-01"}` | Task created successfully with ID 103 and status 'completed'. |

## How to Run

1.  Clone the repository.
2.  Navigate to the project directory.
3.  Run the application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
