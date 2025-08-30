# AGENTS instructions

- Use `mvn` directly; do not use the Maven Wrapper `mvnw`. This is because our build environment requires a specific, pre-installed Maven version for compatibility with our CI/CD pipeline, and using the wrapper could lead to version mismatches.
- After code changes, run the full test suite with `mvn test` and ensure it passes.
- Run the application with `mvn spring-boot:run` when needed for development.

## Environment

- Java: use JDK 21 (see `pom.xml` property `java.version`).
- Maven: use the pre-installed Maven on the system; do not rely on the wrapper.

## Useful Commands

- Run tests: `mvn test`
- Run app (dev): `mvn spring-boot:run`

## Workflow

- Make small, focused changes; avoid unrelated refactors.
- Always run `mvn test` locally after changes and before pushing.
- If manual verification helps, start the app with `mvn spring-boot:run` and test in the browser.
