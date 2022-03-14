# Gradle Command

## Useful gradle commands

- Upgrade gradle wrapper: `./gradlew wrapper --gradle-version x.x.x`
- List available tasks: `./gradlew tasks`
- Stop gradle daemon: `./gradlew --stop`
- Check task dependencies: `./gradlew {some_task} --try-run`
- Run task in a specific sub-project : `./gradlew {sub_project}:{some_task}` (eg. `./gradlew library-core:test`)

## Plugins

- [java library plugin](https://docs.gradle.org/current/userguide/java_library_plugin.html)
- [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
- [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
- [jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- [maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html)
- [shadow jar plugin](https://github.com/johnrengelman/shadow)
- [jmh plugin](https://github.com/melix/jmh-gradle-plugin)
