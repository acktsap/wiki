# Java Application

## Build

- Prerequisite
  - jdk8
  - gradle
- Lint: `./gradlew check`
- Test: `./gradlew test`
- Build (also lint, test): `./gradlew clean build`
- Make distribution: `./gradlew clean build installDist`
  - Distribution on `$PROJECT_HOME/assembly/build/distribution/`
  - Installed on `$PROJECT_HOME/assembly/install/xxx`

## Product

- Install: `tar -xvf java-application-x.x.tar` or `unzip java-application-x.x.zip`
- Run
  - App: `./bin/app`
  - App: `./bin/tools`

## Useful gradle commands

- Upgrade gradle wrapper: `gradle wrapper --gradle-version x.xx`
- List available tasks: `./gradlew tasks --all`

## See also

- [gradle distribution plugin](https://docs.gradle.org/current/userguide/distribution_plugin.html)
- [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
- [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
