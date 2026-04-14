# Study Planner Web (Spring Boot)

This project is a Spring Boot web starter for migrating a Java console-based study planner.

## Included stack

- Spring Boot Web (MVC)
- Spring Security (form login)
- Thymeleaf templates

## Basic structure

- `com.studyplanner.controller` for web endpoints and view handling
- `com.studyplanner.service` for business logic and migration of console logic
- `com.studyplanner.model` for domain objects
- `src/main/resources/templates` for Thymeleaf pages
- `src/main/resources/static` for CSS and static files

## Migration path from console app

1. Move pure logic methods (sorting, recommendation, scheduling) into `ConsoleLogicMigrationService`.
2. Keep input/output out of those methods so they stay testable and reusable.
3. Use controllers only for HTTP request mapping and model binding.
4. Replace `InMemoryStudyTaskService` with JPA later if persistence is needed.

## Run locally

```bash
mvn spring-boot:run
```

Open `http://localhost:8080`.

Login credentials:

- Username: `student`
- Password: `study123`
