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
- Assemble Boot Jar: `./gradlew bootJar`
  - Single jar is generated in `${buildDir}/libs`
- Assemble: `./gradlew nativeCompile`
  - Native file is generated in `${buildDir}/libs` 
