# Java Library

## Build

- Prerequisite
  - jdk8
- Clean: `./gradlew clean`
- Lint: `./gradlew lint`
- Test: `./gradlew test`
  - Coverage (need test task): `./gradlew test coverage` (individual), `./gradlew test allcoverage` (all)
  - Benchmark Test: `./gradle {target_project}:jmh`
- Docs: `./gradlew javadoc` (individual), `./gradlew alljavadoc` (all)
- Build (also lint, test): `./gradlew build`
- Shadow Jar: `./gradle shadowJar` (generated in `./library-assembly/build/libs`)
- Install to local: `./gradlew install`

## Useful gradle commands

- Upgrade gradle wrapper: `./gradlew wrapper --gradle-version x.x.x`
- List available tasks: `./gradlew tasks`
- Stop gradle daemon: `./gradlew --stop`
- Check task dependencies: `./gradlew {some_task} --try-run`
- Run task in a specific sub-project : `./gradlew {sub_project}:{some_task}` (eg. `./gradlew library-core:test`)

## See also

- [editorconfig](https://editorconfig.org/)
- [java library plugin](https://docs.gradle.org/current/userguide/java_library_plugin.html)
- [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
- [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
- [jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- [maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html)
- [shadow jar plugin](https://github.com/johnrengelman/shadow)
- [jmh plugin](https://github.com/melix/jmh-gradle-plugin)
