# AGENTS instructions

- Use `mvn` directly; do not use the Maven Wrapper `mvnw`. This is because our build environment requires a specific, pre-installed Maven version for compatibility with our CI/CD pipeline, and using the wrapper could lead to version mismatches.
- After code changes, run the full test suite with `mvn test` and ensure it passes.
- Run the application with `mvn spring-boot:run` when needed for development.

