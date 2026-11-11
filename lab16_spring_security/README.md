# Lab 16 – Spring Security introduction

Course CRUD API (`/api/v1/courses`) on MySQL (Aiven), protected with Spring Security.

## Setup
Put your own Aiven connection details in `src/main/resources/application.properties`
(`spring.datasource.url`, `username`, `password`), then run `clean spring-boot:run`.

## What was added to the starting code
- `pom.xml`: `spring-boot-devtools`, `spring-boot-starter-security`, `spring-security-test`
  (already listed) **and `spring-boot-starter-thymeleaf`**, which was missing: without it the
  `index`, `dashboard` and `adminDashboard` templates are never rendered.
- `security/WebSecurityConfig` (`@Configuration` + `@EnableWebSecurity`) with three beans:
  `securityFilterChain`, `userDetailsService` (in-memory users, BCrypt passwords) and `passwordEncoder`.
- Small fix in `CourseController.createCourse`: the `Location` header now uses the id of the
  **saved** course (it used the id from the request body, which is always null).

## Users
| Username | Password | Role |
|---|---|---|
| `usera` | `pwd123` | USER |
| `admin` | `admin123` | ADMIN |

## Access rules
| URL | Who can access |
|---|---|
| `/` | everybody (no login) |
| `/dashboard` | role USER (`usera`) |
| `/admindashboard` | role ADMIN (`admin`) |
| `/api/v1/courses/**` | any logged-in user |
| `/login`, `/logout` | everybody |

## Testing with Postman / Thunder Client
1. `GET /api/v1/courses` without logging in redirects to `/login`.
2. `GET /login` returns the login form; copy the hidden `_csrf` value from it.
3. `POST /login` as form data: `username=usera`, `password=pwd123`, `_csrf=<token>`.
4. Requests sent after that (same cookie / session) can reach the API endpoints.
5. Logged in as `usera`, `/admindashboard` returns **403 Forbidden**; as `admin` it works,
   while `/dashboard` returns 403 for `admin`.
