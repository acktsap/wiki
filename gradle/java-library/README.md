# Java Library

## Build

- Prerequisite
  - jdk8
- Lint: `./gradlew check`
- Test: `./gradlew test`
- Build (also lint, test): `./gradlew clean build`
- Install to local: `./gradlew clean publishToMavenLocal`

## Useful gradle commands

- Upgrade gradle wrapper: `./gradlew wrapper --gradle-version x.x.x`
- List available tasks: `./gradlew tasks`

## See also

- [java library plugin](https://docs.gradle.org/current/userguide/java_library_plugin.html)
- [maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html)
- [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
- [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
