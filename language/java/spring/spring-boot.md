# Spring Boot

- [Creating your own auto-configuration](#creating-your-own-auto-configuration)
  - [Implementation (< v2.7)](#implementation--v27)
  - [Implementation (v2.7 <)](#implementation-v27-)
  - [Ordering](#ordering)
  - [Conditional Annotations](#conditional-annotations)
  - [Testing](#testing)
  - [Creating your own starter](#creating-your-own-starter)
- [See also](#see-also)

## Creating your own auto-configuration

- 자동으로 등록되면 좋을 bean들을 등록함. 근데 보통 조건이 붙어서 `@ConditionalXXX` annotation을 사용.

### Implementation (< v2.7)

- `@Configuration`을 써서 자동으로 등록되면 좋을 빈들을 정의.
- 그렇게 정의한 Configuration을 `META-INF/spring.factories`에 다음과 같이 정의해두면 Spring boot가 알아서 읽음.
- 이 방법으로만 등록되어야 함. component scan으로 자동으로 읽게 되지 않게 주의.

```text
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.mycorp.libx.autoconfigure.LibXAutoConfiguration,\
com.mycorp.libx.autoconfigure.LibXWebAutoConfiguration
```

### Implementation (v2.7 <)

- `@AutoConfiguration`을 써서 자동으로 등록되면 좋을 빈들을 정의.
- `@AutoConfiguration`은 `@Configuration`으로 meta annotated 되어있음.
- 그렇게 정의한 Configuration을 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`에 다음과 같이 정의해두면 Spring boot가 알아서 읽음.
- 이 방법으로만 등록되어야 함. component scan으로 자동으로 읽게 되지 않게 주의.

```text
com.mycorp.libx.autoconfigure.LibXAutoConfiguration
com.mycorp.libx.autoconfigure.LibXWebAutoConfiguration
```

### Ordering

- `@AutoConfigureBefore`
- `@AutoConfigureAfter`
- `@AutoconfigureOrder`

### Conditional Annotations

- Class conditions
  - `@ConditionalOnClass`
  - `@ConditionalOnMissingClass`
  - `@ConditionalOnClass`의 대상이 Bean 리턴값 자체일 경우 그냥 bean 정의 method 위에다 붙이면 대상 class가 없는 경우 jvm이 Configuration class를 로딩할 때 에러가 발생함. 이럴때 static nested class를 써서 우회.
    ```java
    @AutoConfiguration
    // Some conditions ...
    public class MyAutoConfiguration {

        // Auto-configured beans ...

        // 이렇게!
        @Configuration(proxyBeanMethods = false)
        @ConditionalOnClass(SomeService.class)
        public static class SomeServiceConfiguration {

            @Bean
            @ConditionalOnMissingBean
            public SomeService someService() {
                return new SomeService();
            }

        }
    }
    ```
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

Rule of Thumb

- `@ConditionalOnClass`, `@ConditionalOnMissingClass`을 meta annotation으로 사용하는 경우 name을 지정해 줘야 함.
- `@ConditionalOnBean`, `@ConditionalOnMissingBean`을 사용할 때 method signature에 가능하면 구체적인 정보를 제공 (eg. interface 말고 concrete class를 제공).

### Testing

- `ApplicationContextRunner` 사용
  ```java
  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(MyServiceAutoConfiguration.class));

  @Test
  void defaultServiceBacksOff() {
      this.contextRunner.withUserConfiguration(UserConfiguration.class).run((context) -> {
          assertThat(context).hasSingleBean(MyService.class);
          assertThat(context).getBean("myCustomService").isSameAs(context.getBean(MyService.class));
      });
  }

  @Configuration(proxyBeanMethods = false)
  static class UserConfiguration {

      @Bean
      MyService myCustomService() {
          return new MyService("mine");
      }
  }
  ```
- eg.
  - [sprign boot autconfigure batch test](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/test/java/org/springframework/boot/autoconfigure/batch)

### Creating your own starter

- 자체 jar는 없고 의존성에 autoconfigure + useful dependencies + library 자체를 추가한 것.
- starter 한개만 import 하면 library를 사용할 수 있게 하는게 취지.
- autoconfigure 할게 뭐 없다면 starter에만 다 때려박아도 ㄱㅊ.
- eg.
  - [spring boot starter actuator](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/spring-boot-starter-actuator/build.gradle)
  - [spring data jdbc plus starter](https://github.com/naver/spring-jdbc-plus/blob/master/spring-boot-starter-data-jdbc-plus-sql/build.gradle)

Naming module

- module 이름은 `spring-boot-xxx`로 짓지 말고 `acme-spring-boot-xxx`처럼 앞에 prefix를 붙이기; 지내들이 나중에 공식 support를 할 수도 있다고.
  > 근데 이거는 xxx-plus 개념에서는 굳이일듯? 아예 공식 repo에 포함되거나 말거나 하는거니까

Configuration Keys

- configuration 이름은 지네들이 사용하는 `server`, `management`, `spring` 같은거 쓰지 마라고함. 하위호환 깨질 수 있다고. prefix는 작성자가 정의한 namespace를 사용해.
  > 이거도 extension 개념으로는 써도 ㄱㅊ을듯.
- Descriptions rule
  - [참고](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#features.developing-auto-configuration.custom-starter.configuration-keys)
- `@ConfigurationProperties` 사용하는 경우 [trigger meta-data generation](https://docs.spring.io/spring-boot/docs/3.0.x/reference/html/configuration-metadata.html#appendix.configuration-metadata.annotation-processor)를 사용해서 `META-INF/spring-configuration-metadata`에 생성해서 jar를 마는게 좋음.
  - autoconfigure module에서는 `spring-boot-autoconfigure-processor`를 사용해서 이걸 자동으로 되게 만들 수 있음.
  ```groovy
  dependencies {
      annotationProcessor "org.springframework.boot:spring-boot-autoconfigure-processor"
  }
  ```

## See also

- Creating auto-configuration
  - [Creating your own auto-configuration (2.7.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#features.developing-auto-configuration)
  - [Creating your own auto-configuration (2.6.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.6.0/reference/htmlsingle/#features.developing-auto-configuration)
  - [Creating your own auto-configuration (2.5.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#features.developing-auto-configuration)
  - [Creating your own auto-configuration (2.4.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/spring-boot-features.html#boot-features-developing-auto-configuration)
  - [Creating your own auto-configuration (2.3.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration)
  - [Creating your own auto-configuration (2.3.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration)
  - [Creating your own auto-configuration (2.2.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration)
  - [Creating your own auto-configuration (2.1.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration)
  - [Creating your own auto-configuration (2.0.0, spring boot official doc)](https://docs.spring.io/spring-boot/docs/2.0.0.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration)
  - [(Spring Boot) spring-boot-configuration-processor 활용하기 (perfectacle)](https://perfectacle.github.io/2021/11/21/spring-boot-configuration-processor/)
- Gradle Template
  - [gradle-kotlin-spring (mrclrchtr)](https://github.com/mrclrchtr/gradle-kotlin-spring)
