# Spring Boot Application Monorepo Gradle

## Features

...

## Build from source

### Prerequisites

- Graalvm17 or higher

### Build

- Clean: `./gradlew clean`
- Check: `./gradlew check --parallel`
- Build: `./gradlew build --parallel`
- Assemble Plain Jar: `./gradlew assembleDist`
  - tar.gz file is generated in `${buildDir}/distributions`
- Assemble Native Cli: `./gradlew nativeCompile`
  - Native file is generated in `${buildDir}/native/nativeCompile` 
