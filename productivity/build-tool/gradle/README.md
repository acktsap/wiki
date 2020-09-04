# Gradle

java진영에서 maven 대신할 build tool

## Concept

### Feature

- High performance : 필요한 task만 실행. task result cache기능.
- JVM foundation : run on JVM. need JDK
- Conventions : 기존 jvm쪽 build tool인 maven의 convention을 따름
- Extensibility : custom Task를 작성해서 확장 가능
- IDE support : Intellij, Eclipse 등 여러 IDE 지원
- Insight : 빌드 실행 정보에 대한 detail을 제공

### Core

- A general-purpose build tool
  - 범용 build tool임. c도 가능. But dependency management를 maven repository쪽만 할 수 있다는게 단점.
  - 그렇다고 다 작성할필요는 ㄴㄴ. 이미 작성된 Plugin을 사용하면 됨 (eg. java-application, java-library)
- Core model is based on **tasks**
  - Based on Directed Acyclic Graphs (DAGs) of tasks
  - Incremental Build : 변경된거만 재빌드
- Fixed build phases
  - Initialization : setup & which project?
  - Configuration : configures the task graph
  - Execution
- Extensible in more ways than one
  - Custom task types : 주로 buildSrc안에 정의
  - Custom action types : `Task.doFirst()`, `Task.toLast()`
  - Extra properties
    ```groovy
     ext {
       springVersion = "3.1.0.RELEASE"
       emailNotification = "build@master.org"
     }
    ```
  - Custom convention, model : By custom plugin. Provide custom DSL.
    ```groovy
    site {
      outputDir = file('build/mysite')
      websiteUrl = 'https://gradle.org'
      vcsUrl = 'https://github.com/gradle/gradle-site-plugin'
    }
    ```
  - Build scripts operate against an API : Avoid putting much, imperative logic in your build scripts. use declarative one (task).

## Installation

- osx : `brew install gradle`
- check : `gradle -v`

## Core command

- wrapper : `gradle wrapper`
- show tasks : `./gradlew tasks`
- show dependencies : `./gradlew :dependencies` or `./gradlew :a:b:dependencies` (where a:b is subproject)


## References

What's gradle

https://docs.gradle.org/current/userguide/what_is_gradle.html

Installation

https://docs.gradle.org/current/userguide/installation.html
