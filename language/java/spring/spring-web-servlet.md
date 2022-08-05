# Spring Web Servlet

- [Introduction](#introduction)
- [Dispatcher Servlet](#dispatcher-servlet)
  - [Servlet](#servlet)
  - [Context Hierarchy](#context-hierarchy)
  - [Dispatcher Model for Framework Contract](#dispatcher-model-for-framework-contract)
  - [Dispatcher Servlet 동작](#dispatcher-servlet-동작)
  - [Usage](#usage)
- [See also](#see-also)

## Introduction

- [spring-mvc 모듈](https://github.com/spring-projects/spring-framework/tree/main/spring-webmvc) 에 대한 것.

## Dispatcher Servlet

- `Servlet` 인데 front controller pattern을 써서 실제 request를 processing하고 response를 rendering하는 일은 configurable delegate component에서 수행됨.
- `Servlet` 처럼 web.xml에 spec이 정의되어야 함

### Servlet

- A Java software component that extends the capabilities of a server.
  > software component란 특정한 기능을 캡슐화 한 친구.
- 여러 요청을 처리할 수 있는 component지만 주로 http requets를 처리.
- filter1 -> filter2 -> ... -> servlet 식으로 처리.

### Context Hierarchy

![dispatcher-servlet-hierarchy](./img/spring-web-servlet-dispatcher-servlet-hierarchy.png)

- Root WebApplicationContext : Service 등 여러 Servlet에서 사용할 bean을 등록.
- Servlet WebApplicationContext : Controller, View Resolver 등 bean을 정의.
- 계층 구조로 되어 있어서 Servlet WebApplicationContext에서 bean을 못찾으면 Root WebApplicationContext에서 찾음. 그런데 보통 Servlet WebApplicationContext 한개면 충분.

### Dispatcher Model for Framework Contract

`DispatcherServlet`

- `doService`에서 실제 servlet 일을 함.
- `doDispatch`에서 front controller 역할을 함.
- `initStrategies` method를 보면 중요한 interface들이 있음.

Delegate들. 이 interface들이 bean으로 있는지 먼저 확인하고 없으면 [DispatcherServlet.properties 의 기본값](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/resources/org/springframework/web/servlet/DispatcherServlet.properties) 사용.

- [`HandlerMapping`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerMapping.java)
  - request를 handler에 mapping시킴.
  - 대표적인 구현체는 `@RequestMapping`를 처리하는 `RequestMappingHandlerMapping`가 있음.
  - HandlerMapping 구현체는 보통 `HandlerInterceptor`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerInterceptor.java) 들을 가지고 있어서 `preHandle(..)`, `postHandle(..)`, `afterCompletion()`을 처리.
- 이것들 모두 contract가 있지만 customizing 은 가능
- [`HandlerAdapter`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerAdapter.java)
  - `DispatcherServlet`가 handler를 호출 할 때 실제 어떻게 호출되는지 등을 알 필요 없게 하기 위한 추상화.
  - 대표적인 구현체는 `@RequestMapping`를 처리하는 `RequestMappingHandlerAdapter`가 있음.
- [`HandlerExceptionResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerExceptionResolver.java)
  - 예외 처리.
- [`ViewResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/ViewResolver.java)
  - handler로부터 받은 view name을 actual view에 mapping 시킴.
- [`LocaleResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/LocaleResolver.java), [`LocaleContextResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/LocaleContextResolver.java)
  - Locale 처리.
- [`ThemeResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/ThemeResolver.java)
  - theme 설정.
- [`MultipartResolver`](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/java/org/springframework/web/multipart/MultipartResolver.java)
  - 파일 업로드 같은 multi part를 처리.
- [`FlashMapManager`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/FlashMapManager.java)
  - 요청간 attribute를 공유할 수 있게 해줌.
  - FlashMap 사용.
  - [`FlashMap`](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/FlashMap.java)
    - HashMap 상속해서 기능 몇개 추가한거.

### Dispatcher Servlet 동작

- `DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE` key로 `WebApplicationContext`를 찾음.
- locale 설정이 있는 경우 `LocaleResolver`를 binding.
- theme 설정이 있는 경우 `ThemeResolver`를 binding.
- multipart가 있는 경우 `MultipartResolver`를 사용하여 `HttpServletRequest`를 [`MultipartHttpServletRequest`](https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/main/java/org/springframework/web/multipart/MultipartHttpServletRequest.java)로 wrapping 시킴.
- Handler를 찾아서 실행.
- Handler에서 model을 리턴하는 경우 `ViewResolver`를 사용해서 적절한 view를 render.
- 이 전체 과정에서 나는 에러는 `HandlerExceptionResolver`이 처리.

### Usage

- [legacy servlet](spring-legacy-servlet/src/main/webapp/WEB-INF/web.xml)
- delegate 들 `@EnableWebMvc`로 설정 예시 : todo, https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-config 참고

## See also

- [spring web servlet (official)](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Jakarta Servlet (wiki)](https://en.wikipedia.org/wiki/Jakarta_Servlet)
- [Component-based software engineering, Software component (wiki)](https://en.wikipedia.org/wiki/Component-based_software_engineering#Software_component)