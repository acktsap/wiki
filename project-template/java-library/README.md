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
