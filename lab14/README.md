# Lab 14 – Spring Boot CRUD API (courses)

Course Web API with all CRUD operations, Spring Data JDBC, an in-memory H2 database and input validation.

Run: `clean spring-boot:run`, the server listens on **port 3000**: http://localhost:3000/api/v1/courses

| Operation | Request | Success | Errors |
|---|---|---|---|
| Read all | `GET /api/v1/courses` | 200 | |
| Read one | `GET /api/v1/courses/{id}` | 200 | 404 not found, 400 id ≤ 0 |
| Create | `POST /api/v1/courses` | 201 + `Location` | 400 invalid body or id sent |
| Update | `PUT /api/v1/courses/{id}` | 204 | 404 not found, 400 invalid |
| Delete | `DELETE /api/v1/courses/{id}` | 204 | 404 not found |

Example body: `{"code":"SE322","name":"Software requirements","description":"Requirements engineering","credits":3}`

Validation (`spring-boot-starter-validation`): code and name required, name 2–100 characters,
credits 1–10, and the id in the URL must be positive.

Note: there was no instruction sheet for lab 14 in the labs folder, only `lab14_full_solution.rar`,
so the requirements were taken from that solution. Differences from it: the `Location` header
includes `/courses/` (the solution returned `/api/v1/{id}`), credits are required, and every
`{id}` is validated as positive.
