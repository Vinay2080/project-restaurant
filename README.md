# Project Restaurant - Spring Boot REST API
 
 A Spring Boot backend for a restaurant management system with JWT authentication (asymmetric RSA keys), role-based access control, and a PostgreSQL database.
 
 ## What’s in this repo
 - **Authentication**: Register, login, refresh token, and `me` endpoint
 - **Role-based access control** using Spring Security method annotations (`@PreAuthorize`)
 - **Restaurant modules**:
   - Staff management
   - Salary / payroll management
   - Restaurant table management
   - Table booking management
 
 ---
 
 ## Tech Stack
 | Layer | What |
 |------|------|
 | Runtime | Java 25 |
 | Framework | Spring Boot 3.5.6 |
 | Web | Spring Web (WebMVC) |
 | Security | Spring Security + JWT (JJWT 0.13.0) |
 | Persistence | Spring Data JPA |
 | Database | PostgreSQL |
 | API Docs | springdoc-openapi (Swagger UI) |
 | Mapping | MapStruct |
 
 ---
 
 ## Run Locally
 
 ### Prerequisites
 - Java 25
 - Maven 3.9+
 - PostgreSQL
 
 ### Configuration
 This project imports environment variables from `.env` (optional):
 - `spring.config.import: optional:file:.env[.properties]`
 
 Required variables (from `src/main/resources/application.yaml`):
 - `db_url`
 - `db_port`
 - `db_name`
 - `db_username`
 - `db_password`
 - `db_timezone`
 
 Resulting JDBC URL:
 - `jdbc:postgresql://${db_url}:${db_port}/${db_name}`
 
 **Note:** `spring.jpa.hibernate.ddl-auto` is currently set to `create` (schema is recreated on startup).
 
 ### JWT Keys
 JWT signing uses RSA keys bundled under:
 - `src/main/resources/local-keys/private_key.pem`
 - `src/main/resources/local-keys/public_key.pem`
 
 ### Start
 ```bash
 mvn spring-boot:run
 ```
 
 ---
 
 ## Default Seed Data
 On startup, `DataInitializer` ensures roles exist:
 - `ROLE_USER`, `ROLE_STAFF`, `ROLE_MANAGER`, `ROLE_ADMIN`
 
 It also seeds a default admin user if missing:
 - **Email**: `admin@restaurant.com`
 - **Password**: `Admin_123!`
 
 ---
 
 ## Swagger / OpenAPI
 Once the app is running:
 - `http://localhost:8080/swagger-ui/index.html`
 
 ---
 
 ## API Endpoints
 
 ### Auth (`/api/v1/auth`)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/login` | Authenticate with email/password and return access + refresh tokens |
 | POST | `/register` | Register a new user (creates `ROLE_USER`) |
 | POST | `/refresh/token` | Exchange refresh token for a new access token |
 | GET | `/me` | Get current user info from JWT |
 
 ### User (`/api/user`)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | PATCH | `/update/profile` | Update profile for the authenticated user |
 | POST | `/update/password` | Change password for the authenticated user |
 | PATCH | `/deactivate` | Deactivate current account |
 | PATCH | `/reactivate` | Reactivate current account |
 | DELETE | `/delete` | Delete current account |
 
 ### Admin (`/api/admin`) (ADMIN only)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/managers` | Create manager |
 | GET | `/managers/{managerID}` | Get manager by id |
 | GET | `/managers` | List managers (paged) |
 | PUT | `/managers/{managerID}` | Update manager |
 | PATCH | `/managers/{managerID}/deactivate` | Deactivate manager |
 | PATCH | `/managers/{managerID}/reactivate` | Reactivate manager |
 | DELETE | `/managers/{managerID}` | Delete manager |
 | GET | `/managers/deactivated` | List deactivated managers (paged) |
 
 ### Staff (`/api/staff`) (ADMIN/MANAGER)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/` | Create staff (ADMIN only) |
 | GET | `/{staffId}` | Get staff by id |
 | GET | `/user/{userId}` | Get staff by user id |
 | GET | `/` | List staff (paged) |
 | GET | `/active?active=true` | Filter by active flag (paged) |
 | GET | `/position/{position}` | Filter by position (paged) |
 | GET | `/employment-type/{employmentType}` | Filter by employment type (paged) |
 | PUT | `/{staffId}` | Update staff |
 | PATCH | `/{staffId}/deactivate` | Deactivate staff (ADMIN only) |
 | PATCH | `/{staffId}/reactivate` | Reactivate staff (ADMIN only) |
 | DELETE | `/{staffId}` | Delete staff (ADMIN only) |
 
 ### Salaries (`/api/salaries`) (ADMIN/MANAGER)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/` | Create salary (ADMIN only) |
 | GET | `/{salaryId}` | Get salary by id |
 | GET | `/staff/{staffId}` | List salaries for staff (paged) |
 | GET | `/?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | List salaries in date range (paged) |
 | GET | `/status/{isPaid}` | Filter by paid status (paged) |
 | PATCH | `/{salaryId}/mark-paid` | Mark salary as paid (ADMIN only) |
 | DELETE | `/{salaryId}` | Delete salary (ADMIN only) |
 | POST | `/generate-monthly?paymentDate=YYYY-MM-DD&notes=` | Generate monthly salaries (ADMIN only) |
 | GET | `/staff/{staffId}/statistics` | Salary statistics for a staff member |
 | GET | `/payroll-summary?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | Payroll summary |
 
 ### Tables (`/api/v1/tables`)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/` | Add table (ADMIN/MANAGER) |
 | PUT | `/{tableID}` | Update table (ADMIN/MANAGER) |
 | DELETE | `/{tableID}` | Delete table (ADMIN/MANAGER) |
 | GET | `/{tableID}` | Get table by id (ADMIN/MANAGER/STAFF) |
 | GET | `/` | List tables (ADMIN/MANAGER/STAFF) |
 | GET | `/available` | List available tables |
 | GET | `/capacity/{minCapacity}` | List tables by min capacity (ADMIN/MANAGER/STAFF) |
 | PATCH | `/{tableID}/status/{status}` | Update table status (ADMIN/MANAGER) |
 
 ### Bookings (`/api/v1/bookings`)
 | Method | Endpoint | Purpose |
 |---|---|---|
 | POST | `/` | Book a table (authenticated) |
 | GET | `/{bookingID}` | Get booking by id (authenticated) |
 | GET | `/my-bookings` | Current user bookings (authenticated) |
 | GET | `/table/{tableID}` | Bookings for a table (ADMIN/STAFF) |
 | GET | `/?startDate=YYYY-MM-DDTHH:mm:ss&endDate=YYYY-MM-DDTHH:mm:ss` | Bookings in date range (ADMIN/MANAGER/STAFF) |
 | PATCH | `/{bookingID}/status/{status}` | Update booking status (ADMIN/STAFF) |
 | DELETE | `/{bookingID}` | Cancel booking (authenticated) |
 | GET | `/availability?tableID=...&startTime=YYYY-MM-DDTHH:mm:ss&endTime=YYYY-MM-DDTHH:mm:ss` | Check table availability |
 
 ---
 
 ## Error Handling
 Errors are handled centrally in `ApplicationExceptionHandler` using `@RestControllerAdvice`.
 
 ---
 
 ## Notes
 - Security rules are enforced primarily via `@PreAuthorize` on controllers.
 - `SecurityConfig.public_urls` currently contains `"/**"` (permit-all) for testing; tighten this for production.