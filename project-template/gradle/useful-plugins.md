# Useful Plugins

- [Base](#base)
- [Distribution](#distribution)
- [Java](#java)
- [Java Library](#java-library)
  - [vs Maven Scope](#vs-maven-scope)
- [Java Checkstyle](#java-checkstyle)
- [Java Coverage](#java-coverage)
- [Java MicroBmt](#java-microbmt)
- [Java Publish](#java-publish)
- [Kotlin Jvm](#kotlin-jvm)
- [Kotlin ktlint](#kotlin-ktlint)
- [Kotlin Test](#kotlin-test)
- [Spring](#spring)
- [Graalvm Native](#graalvm-native)
- [Shadow Jar](#shadow-jar)
- [Sonarqube](#sonarqube)
- [See also](#see-also)

## Base

- 대부분의 build사용되는 task들을 제공.

https://docs.gradle.org/current/userguide/base_plugin.html

## Distribution

- [Distribution plugin](https://docs.gradle.org/current/userguide/distribution_plugin.html)
  - distTar : Tar 마는거.
  - distZip : Zip 마는거.
  - assembleDist : distZip + distTar.
  - installDist : `${buildDir}/install`에 설치.

## Java

- [java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)

## Java Library

- [java library plugin](https://docs.gradle.org/current/userguide/java_library_plugin.html)
- Configuration effect
  - `api`
    - classpath : compile, runtime, test compile, test runtime
    - transitive
    - generated pom : `compile`
  - `implementation`
    - classpath : compile, runtime, test compile, test runtime
    - generated pom : `runtime`
  - `runtimeOnly`
    - classpath : runtime, test runtime
    - generated pom : `runtime`
  - `compileOnly`
    - classpath : compile, test compile
    - generated pom : none
  - `testXXX`
    - compile classpath : depends on `XXX`
    - generated pom : none

see also

- [Introducing Compile-Only Dependencies](https://blog.gradle.org/introducing-compile-only-dependencies)

### vs Maven Scope

[maven scope](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#dependency-scope)

- `compile`
  - classpath : all
  - gradle : `api`
- `provided`
  - classpath : compile, test
  - gradle : `compileOnly`
- `runtime`
  - classpath : runtime, test
  - gradle : `runtimeOnly`, `testImplementation`
- `test`
  - classpath : test
  - gradle : `testImplementation`
- `optional`
  - gradle : none

> 뭔가 헷갈리는데 이게 maven하고 gradle하고 바라보는 관점이 달라서 그런듯.

## Java Checkstyle

- [gradle checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
  - [google checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
  - [naver checkstyle](https://github.com/naver/hackday-conventions-java/blob/master/rule-config/naver-checkstyle-rules.xml)

## Java Coverage

- [jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
  - byte code 기반으로 처리해서 kotlin code도 일부 제외하고 처리 가능.

## Java MicroBmt

- [jmh plugin](https://github.com/melix/jmh-gradle-plugin)

## Java Publish

- [maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html)
- [shadow jar plugin](https://github.com/johnrengelman/shadow)
- [Signing Plugin](https://docs.gradle.org/current/userguide/signing_plugin.html)
  - Support for generating OpenPGP signatures.
  - [Signatory crednetials](https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials)

See also

- [sonatype publish requirements](http://central.sonatype.org/publish/requirements/)

## Kotlin Jvm

- [Kotlin Jvm](https://kotlinlang.org/docs/gradle.html#targeting-the-jvm)

## Kotlin ktlint

- [ktlint-gradle](https://github.com/JLLeitschuh/ktlint-gradle)

## Kotlin Test

- [Kotlinx-Kover](https://github.com/Kotlin/kotlinx-kover)
  - Kotlin coverage gradle plugin
  - Jacoco doesn't support some feature like kotlin inline function.
  - [Kover – The Code Coverage Plugin (youtube)](https://www.youtube.com/watch?v=jNu5LY9HIbw)
  - [Kover : Code Coverage plugin for Kotlin](https://lengrand.fr/kover-code-coverage-plugin-for-kotlin/)

## Spring

- [Spring Boot Plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)
  - Allows you to package executable jar or war archives, run Spring Boot applications, and use the dependency management provided by spring-boot-dependencies.
  - The plugin detects when certain other plugins are applied and reacts accordingly. (eg. `spring.dependency.management`, `java`, `groovy`, ...)
- [Spring Dependency Management Plugin](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/)
  - Control the versions of your project’s direct and transitive dependencies and will honour any exclusions declared in the poms of your project’s dependencies.
- [kotlin-spring](https://kotlinlang.org/docs/all-open-plugin.html#spring-support)
  - Mark `open` on Proxy based one.
    - @Component
    - @Async
    - @Transactional
    - @Cacheable
    - @SpringBootTest
    - ...

## Graalvm Native

- [Native Build Tools](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

## Shadow Jar

- [Shadow Jar](https://imperceptiblethoughts.com/shadow/introduction)
  - Make a fat Jar.

## Sonarqube

- [sonarqube](https://plugins.gradle.org/plugin/org.sonarqube)

## See also

- [maven-scopes-gradle-configurations (refactoring.io)](https://reflectoring.io/maven-scopes-gradle-configurations)
