# Spring Boot Library Monorepo Gradle

## Features

...

## Install

### Requirements

### Gradle

```groovy
...
```

### Maven

```xml
...
```

## Build from source

### Prerequisites

- Jdk8 or higher

### Build

- Clean: `./gradlew clean`
- Check: `./gradlew check`
  - Coverage report : `${buildDir}/kover/html/index.html`
- Verify coverage: `./gradlew koverMergedVerify`
- Merge coverage reports (after generating each report): `./gradlew koverMergedReport`
  - Merged coverage report : `${rootDir}/${buildDir}/kover/html/index.html`
- Build: `./gradlew build`
- Install to local: `./gradlew install`
- Publish: `./gradlew publish`

## Contributing

...
