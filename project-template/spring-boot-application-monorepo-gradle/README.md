# Spring Boot Application Monorepo Gradle

## Features

...

## Build from source

### Prerequisites

- Graalvm17 or higher

### Build

- Clean: `./gradlew clean`
- Check: `./gradlew check`
  - Coverage report : `${buildDir}/kover/html/index.html`
- Verify coverage: `./gradlew koverMergedVerify`
- Merge coverage reports (after generating each report): `./gradlew koverMergedReport`
  - Merged coverage report : `${rootDir}/${buildDir}/kover/html/index.html`
- Build: `./gradlew build`
- Assemble Plain Jar: `./gradlew assembleDist`
  - `*.tar.gz` will be generated in `${buildDir}/distributions`
- Assemble Native Cli: `./gradlew nativeCompile`
  - Native file will be generated in `${buildDir}/native/nativeCompile`
