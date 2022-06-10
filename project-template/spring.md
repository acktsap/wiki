## Spring

- [Spring](#spring)
- [Creating Spring Library](#creating-spring-library)
  - [Creating your own auto-configuration](#creating-your-own-auto-configuration)
  - [Creating your own starter](#creating-your-own-starter)
- [Gradle Support](#gradle-support)
- [Reference](#reference)

## Creating Spring Library

### Creating your own auto-configuration

todo: 최신 버전에 맞게 수정

- 자동으로 등록되면 좋을 bean들을 `@Configuration`을 통해서 등록함. 근데 보통 조건이 붙어서 `@ConditionalXXX` annotation을 사용.
  - Class conditions
    - `@ConditionalOnClass`
    - `@ConditionalOnMissingBean`
  - Bean conditions
    - `@ConditionalOnBean`
    - `@ConditionalOnMissingBean`
  - Property conditions
    - `@ConditionalOnProperty`
  - Resource conditions
    - `@ConditionalOnResource`
  - Web application conditions
    - `@ConditionalOnWebApplication`
    - `@ConditionalOnNotWebApplication`
  - SpEL expression conditions
    - `@ConditionalOnExpression`
- 그렇게 정의한 Configuration을 `META-INF/spring.factories`에 다음과 같이 정의해두면 Spring boot가 알아서 읽음.
  ```text
  org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.mycorp.libx.autoconfigure.LibXAutoConfiguration,\
  com.mycorp.libx.autoconfigure.LibXWebAutoConfiguration
  ```
- AutoConfiguration 순서도 정할 수 있음.
  - `@AutoConfigureBefore`
  - `@AutoConfigureAfter`
  - `@AutoconfigureOrder`

### Creating your own starter

- `build.gradle`에 다음의 의존성을 가지고 있음
  - library 자체
  - autoconfigure
  - 추가로 있으면 좋은 library들
  - eg.
    - [spring boot starter actuator](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-actuator/build.gradle)
    - [spring data jdbc plus starter](https://github.com/naver/spring-jdbc-plus/blob/master/spring-boot-starter-data-jdbc-plus-sql/build.gradle)
- `@ConfigurationProperties` 사용하는 경우 [trigger meta-data generation](https://docs.spring.io/spring-boot/docs/3.0.x/reference/html/configuration-metadata.html#appendix.configuration-metadata.annotation-processor)를 사용해서 `META-INF/spring-configuration-metadata`에 생성해서 jar를 마는게 좋음.
  
주의사항

- 이름은 공식 문서에서는 `spring-boot-xxx`로 짓지 말고 `acme-spring-boot-xxx`처럼 앞에 prefix를 붙이라고함; 지내들이 나중에 공식 support를 할 수도 있다고.
  > 근데 이거는 xxx-plus 개념에서는 굳이일듯? 아예 공식 repo에 포함되거나 말거나 하는거니까
- configuration 이름은 지네들이 사용하는 `server`, `management`, `spring` 같은거 쓰지 마라고함. 하위호환 깨질 수 있다고.  
  > 이거도 extension 개념으로는 써도 ㄱㅊ을듯.

## Gradle Support

- [Spring Boot Plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)
  - Allows you to package executable jar or war archives, run Spring Boot applications, and use the dependency management provided by spring-boot-dependencies.
  - The plugin detects when certain other plugins are applied and reacts accordingly. (eg. `spring.dependency.management`, `java`, `groovy`, ...)
- [Spring Dependency Management Plugin](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/)
  - Control the versions of your project’s direct and transitive dependencies and will honour any exclusions declared in the poms of your project’s dependencies.

## Reference

- Creating auto-configuration
  - [Creating your own auto-configuration (spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.7.1-SNAPSHOT/reference/htmlsingle/#features.developing-auto-configuration)
  - [(Spring Boot) spring-boot-configuration-processor 활용하기 (perfectacle)](https://perfectacle.github.io/2021/11/21/spring-boot-configuration-processor/)
- Gradle Template
  - [gradle-kotlin-spring (mrclrchtr)](https://github.com/mrclrchtr/gradle-kotlin-spring)
