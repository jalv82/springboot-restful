### Java example

RESTful for a CRUD example application.

#### Features:

  - Java 13
  - HTTP verbs (GET, POST, PUT, DELETE)
  - Entity validation (And DTO object with custom annotation)
  - Global exception handling (And custom exception)
  - Custom HTTP error codes (400, 404, 406)
  - Testing (JUnit 5 + Hamcrest)
  - Dependency Management (Maven)
  - Connect it to a database (H2)

#### Frameworks and libraries
  - Spring Boot v2.2.1.RELEASE
  - OpenAPI v1.2.15
  - Lombok v1.18.10
  - Model Mapper v2.3.0
  - H2 v1.4.200
  - JUnit v5.5.2

#### API
  - Data format: **JSON**

#### Endpoints

API REST: http://localhost:7070/restful/swagger-ui.html

  - GET ALL: Get all users from database
  - GET: Specify and user id to get an user
  - CREATE: Create a new user
  - UPDATE: Update an existing user
  - REMOVE: Remove an existing user