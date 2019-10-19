# Java application

## Build from source

- Prerequisite
  - jdk8
  - gradle
- Test : `./gradlew test`
- Build : `./gradlew clean build`
- Make distribution : `./gradlew clean build installDist`
  - Distribution on `$PROJECT_HOME/assembly/build/distribution/`
  - Installed on `$PROJECT_HOME/assembly/install/xxx`

## Product

- Install : `tar -xvf java-application-x.x.tar` or `unzip java-application-x.x.zip` 
- Run : `./bin/app`, `./bin/tools`

## Update gradle wrapper

```sh
> gradle wrapper --gradle-version x.xx
```

## See also

[gradle plugin](https://docs.gradle.org/current/userguide/distribution_plugin.html)
