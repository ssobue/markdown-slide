# AGENTS instructions

- Use the Gradle Wrapper (`./gradlew`) for all build and development commands.
- After code changes, run the full test suite with `./gradlew test` and ensure it passes.
- Run the application with `./gradlew bootRun` when needed for development.

## Environment

- Java: use JDK 25 (see `build.gradle` toolchain configuration).
- Gradle: use the checked-in Gradle Wrapper.

## Useful Commands

- Run tests: `./gradlew test`
- Run app (dev): `./gradlew bootRun`

## Workflow

- Make small, focused changes; avoid unrelated refactors.
- Always run `./gradlew test` locally after changes and before pushing.
- If manual verification helps, start the app with `./gradlew bootRun` and test in the browser.
- NullAway runs during Java compilation for handwritten source. Keep packages null-marked with JSpecify.
- When adding a `package-info.java`, include a package Javadoc comment before `@NullMarked`.
- If validation reveals additional warnings, failures, or cleanup opportunities outside the requested scope,
  confirm the intended fix with the user before changing them.
