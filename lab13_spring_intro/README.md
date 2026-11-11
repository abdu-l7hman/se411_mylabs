# Lab 13 – Spring Boot introduction

Products Web API with Spring Boot, Spring Data JDBC and an in-memory H2 database.

Run: `clean spring-boot:run` (or `mvnw spring-boot:run`), then open http://localhost:8080/api/v1/products

| Request | Result |
|---|---|
| `GET /api/v1/products` | all products (JSON) |
| `GET /api/v1/products/12` | product 12 |
| `GET /api/v1/products/999` | 404 Not Found |
| `GET /api/v1/products/-5` | **400 Bad Request** (id must be positive) |
| `POST /api/v1/products` with `{"name":"PC 09","description":"A portable computer","price":1999.99}` | 201 Created, `Location` header points to the new product |
| `POST` with an empty name or a negative price | 400 Bad Request |

**Step 13:** the price is a `BigDecimal` in the record and `DECIMAL(10,2)` in `schema.sql`, so money keeps exactly 2 decimals (no `0.1 + 0.2 != 0.3` rounding errors).

**Step 14 – which status code do you get for a negative id?**
**400 Bad Request.** `spring-boot-starter-validation` adds Jakarta Bean Validation; the `@Positive` constraint on the `id` path variable rejects the request before it reaches the service, and Spring answers with 400 and a problem-details body explaining that the id must be positive.

Note: the project uses Spring Boot 4.0.9 (same version as lab 14) instead of 4.1.2.
