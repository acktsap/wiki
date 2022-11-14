# Guides

- [Structuring Individual Builds](#structuring-individual-builds)
- [Structuring Software Products](#structuring-software-products)
- [Authoring Sustainable Builds](#authoring-sustainable-builds)
- [Authoring JVM Builds](#authoring-jvm-builds)
- [Samples](#samples)
- [Maven Bom](#maven-bom)
- [Working with Dependencies](#working-with-dependencies)
- [Best Practices](#best-practices)

## Structuring Individual Builds

- [Structuring and Building a Software Component with Gradle](https://docs.gradle.org/current/userguide/multi_project_builds.html)
- [Declaring Dependencies between Subprojects](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html)
  - 이거
    ```kotlin
    dependencies {
        runtimeOnly(project(":producer"))
    }
    ```
- [Sharing Build Logic between Subprojects](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html)

## Structuring Software Products

https://docs.gradle.org/current/userguide/structuring_software_products.html

## Authoring Sustainable Builds

- [Organizing Gradle Projects](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html)
  - buildSrc
    - 이 directoryr가 있으면 gradle이 읽어서 build script의 classpath에 추가함.
    - 안에 imperative logic을 custom plugin을 넣어서 추가.
- [Best practices for authoring maintainable builds](https://docs.gradle.org/current/userguide/authoring_maintainable_build_scripts.html)
  - Avoid using imperative logic in scripts

## Authoring JVM Builds

- [Building Java & JVM projects](https://docs.gradle.org/current/userguide/building_java_projects.html)
- [Testing Java & JVM projects](https://docs.gradle.org/current/userguide/java_testing.html)

## Samples

https://docs.gradle.org/current/samples/

- [build organization](https://docs.gradle.org/current/samples/#build_organization)
- [java](https://docs.gradle.org/current/samples/#java)
- [kotlin](https://docs.gradle.org/current/samples/#kotlin)

## Maven Bom

https://reflectoring.io/maven-bom/

## Working with Dependencies

- [Dependency management in Gradle](https://docs.gradle.org/current/userguide/dependency_management.html)
- [Sharing dependency versions between projects](https://docs.gradle.org/current/userguide/platforms.html)
  - version catalog

## Best Practices

- [gradle-best-practices](https://tomgregory.com/gradle-best-practices)