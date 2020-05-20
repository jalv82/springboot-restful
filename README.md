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
 
  - H2 v1.4.200
  - Hamcrest v2.2
  - JMeter v3.1.0
  - JUnit v5.6.2
  - Lombok v1.18.12
  - Maven v3.6.3
  - Model Mapper v2.3.0
  - OpenAPI v1.3.9
  - Spring Boot v2.3.0.RELEASE

#### API

  - Data format: **JSON**

#### Endpoints

API REST: http://localhost:7070/restful/swagger-ui/index.html?url=/restful/v3/api-docs

  - GET ALL: Get all users from database
  - GET: Specify and user id to get an user
  - CREATE: Create a new user
  - UPDATE: Update an existing user
  - REMOVE: Remove an existing user