# Kotlin Library

## Closed

- 생성자 노출 방어
  ```kotlin
  // internal constructor
  class MockHttpServletRequestDsl internal constructor (private val builder: MockHttpServletRequestBuilder) {
    ...
  }
  ```

## Dsl

- [ktor](https://ktor.io/docs/routing-in-ktor.html)
- [spring bean dsl](https://docs.spring.io/spring-framework/docs/5.0.0.RELEASE/spring-framework-reference/kotlin.html#bean-definition-dsl)
- [spring security kotlin dsl](https://github.com/spring-projects-experimental/spring-security-kotlin-dsl)
  - [pr](https://github.com/spring-projects/spring-security/commit/2df1099da5116f893ef2a09bab4c9cc40527e767)
    - `src/main/kotlin/...` 안에 kotlin module 추가
  - [ServerHttpSecurityDsl](https://github.com/spring-projects/spring-security/blob/main/config/src/main/kotlin/org/springframework/security/config/web/server/ServerHttpSecurityDsl.kt)
    - 객체 자체를 invoke하는 식으로 extension
      ```kotlin
      // operator
      operator fun ServerHttpSecurity.invoke(httpConfiguration: ServerHttpSecurityDsl.() -> Unit): SecurityWebFilterChain =
          ServerHttpSecurityDsl(this, httpConfiguration).build()

      // usage
      @Bean
      fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
          authorizeExchange {
            exchange("/public", permitAll)
            exchange(anyExchange, authenticated)
          }
        }
      }
      ```

## Java Kotlin Extension

- [Jackcon Kotlin Module](https://github.com/FasterXML/jackson-module-kotlin/tree/2.13/src/main/kotlin/com/fasterxml/jackson/module/kotlin)
  - [generics](https://github.com/FasterXML/jackson-module-kotlin/blob/2.13/src/main/kotlin/com/fasterxml/jackson/module/kotlin/Extensions.kt)
    - extensions `inline fun <reified T> test()`로 (`fun <T> test(type: KClass<T>)` 말고)
- [RestOperaationsExtensions](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/kotlin/org/springframework/web/client/RestOperationsExtensions.kt)
  - [pr](https://github.com/spring-projects/spring-framework/commit/546687d5e44c6771a95f5334dbcbf4b37a6cea33)
    - kotlin을 optional로
