# Gradle

- [Useful commands](#useful-commands)
- [Structuring Software Products](#structuring-software-products)
- [Authoring Sustainable Builds](#authoring-sustainable-builds)
- [Authoring JVM Builds](#authoring-jvm-builds)
- [Samples](#samples)
- [Spring](#spring)
- [Useful Plugins](#useful-plugins)

## Useful commands

```sh
# upgrade gradle wrapper
./gradlew wrapper --gradle-version x.x.x

# list available tasks
./gradlew tasks

# stop gradle daemon
./gradlew --stop

# check task with dry run
./gradlew {some_task} --dry-run

# show compileClassPath dependencies
./gradlew dependencies --configuration compileClasspath

# run task in sub-project
# exmaple) ./gradlew library-core:test
./gradlew {sub_project}:{some_task} 

# exmaple) ./gradlew library-core:test
```

## Structuring Software Products

https://docs.gradle.org/current/userguide/structuring_software_products.html

## Authoring Sustainable Builds

https://docs.gradle.org/current/userguide/organizing_gradle_projects.html

## Authoring JVM Builds

- [Building Java & JVM projects](https://docs.gradle.org/current/userguide/building_java_projects.html)
- [Testing Java & JVM projects](https://docs.gradle.org/current/userguide/java_testing.html)

## Samples

https://docs.gradle.org/current/samples/

- [build organization](https://docs.gradle.org/current/samples/#build_organization)
- [java](https://docs.gradle.org/current/samples/#java)
- [kotlin](https://docs.gradle.org/current/samples/#kotlin)

## Spring

- [Spring Boot Plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)
  - Allows you to package executable jar or war archives, run Spring Boot applications, and use the dependency management provided by spring-boot-dependencies.
  - The plugin detects when certain other plugins are applied and reacts accordingly. (eg. `spring.dependency.management`, `java`, `groovy`, ...)
- [Spring Dependency Management Plugin](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/)
  - Control the versions of your project’s direct and transitive dependencies and will honour any exclusions declared in the poms of your project’s dependencies.

see also

https://github.com/mrclrchtr/gradle-kotlin-spring

## Useful Plugins

- library
  - [java library plugin](https://docs.gradle.org/current/userguide/java_library_plugin.html)
- lint
  - java checkstyle
    - [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
    - [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
    - [naver checkstyle](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-rules.xml)
  - ktlint
    - [ktlint-gradle](https://github.com/JLLeitschuh/ktlint-gradle)
- test
  - [jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
  - [jmh plugin](https://github.com/melix/jmh-gradle-plugin)
- publish
  - [maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html)
  - [shadow jar plugin](https://github.com/johnrengelman/shadow)

