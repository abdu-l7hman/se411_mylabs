# Lab 15 – Spring CRUD Web API with MySQL

The products Web API (lab 13) with all five CRUD operations, moved from the in-memory H2
database to a MySQL server on Aiven.

## Setup (Part A)
1. Create a free MySQL service on https://aiven.io and a database (for example `se411db`).
2. In `src/main/resources/application.properties`, replace `HOST`, `PORT`, `DATABASE` and
   `YOUR_AIVEN_PASSWORD` with your own connection details. Don't push your real password to GitHub.
3. Run `clean spring-boot:run`, then use http://localhost:8080/api/v1/products

## H2 → MySQL changes (Part B)
- `schema.sql`: `DROP TABLE IF EXISTS product;` first (MySQL keeps data between runs),
  `AUTO_INCREMENT` id, `DECIMAL(8,2)` price.
- `data.sql`: inserts without the `id` column, MySQL generates it.
- `pom.xml`: `mysql-connector-java 8.0.33` instead of `h2`.
- `application.properties`: datasource URL, user, password, driver and `spring.sql.init.mode=always`.

## The five CRUD operations (Part C)
| Operation | Request | Success | Errors |
|---|---|---|---|
| Read all | `GET /api/v1/products` | 200 | |
| Read one | `GET /api/v1/products/{id}` | 200 | 404, 400 if id ≤ 0 |
| Create | `POST /api/v1/products` | 201 + `Location` | 400 invalid body |
| Update | `PUT /api/v1/products/{id}` | 204 | 404, 400 invalid body |
| Delete | `DELETE /api/v1/products/{id}` | 204 | 404 |

Example body: `{"name":"PC 09","description":"A portable computer","price":1999.99}`
