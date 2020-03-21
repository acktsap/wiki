# Spring Web MVC

- [Spring Web MVC](#spring-web-mvc)
  - [Servlet](#servlet)
    - [Servlet 동작 과정](#servlet-%eb%8f%99%ec%9e%91-%ea%b3%bc%ec%a0%95)
    - [Servlet Listener](#servlet-listener)
    - [Servlet Filter](#servlet-filter)
  - [DispatcherServlet](#dispatcherservlet)
    - [Dispatcher Servlet 동작 과정](#dispatcher-servlet-%eb%8f%99%ec%9e%91-%ea%b3%bc%ec%a0%95)
    - [Dispatcher Servlet over Servlet Container](#dispatcher-servlet-over-servlet-container)
    - [Embedded Tomcat](#embedded-tomcat)
  - [References](#references)

## Servlet

자바를 사용하여 웹페이지를 동적으로 생성하는 서버측 프로그램 혹은 스펙. Servlet Container위에서 돌아간다.

### Servlet 동작 과정

1. Servlet Container가 Servlet을 init한다 (요청시)
2. 요청을 받을 시 doService가 처리한다. 보통 doGet이나 doPost같은 것으로 처리한다.
3. Servlet Container가 종료될 때 destroy를 호출한다.

### Servlet Listener

Web Application에서 발행하는 이벤트를 감지하고 이벤트에 해당하는 작업을 하는 component

### Servlet Filter

들어온 요청을 서블릿으로 보내고, 또 서블릿이 작성한 응답을 클라이언트로 보내기 전에 전처리를 하는 component. 보통 filter chain형태로 구현

## DispatcherServlet

Servlet스펙에 대한 구현체로 하나의 컨트롤러 (Front Controller)로 요청을 받아서 dispatch해서 처리를 하는 서블릿

### Dispatcher Servlet 동작 과정

![dispatcher-servlet-process](./img/dispatcher-servlet-process.png)

1. Request
2. HandlerMapping : Handler (controller)를 찾음
3. HandlerIntercepter : pre-processing
4. HandlerAdaptor : Handler (controller)를 실행
5. HandlerIntercepter : post-processing
6. ViewResolver : view를 찾음
7. Render view

### Dispatcher Servlet over Servlet Container

DispatcherServlet을 Servlet Container에 등록 (eg. web.xml)\
`ContextLoaderListener`라는 Lister 구현체가 ApplicatonContext를 초기화\
Servlet Container에 *.war 파일이 올라간 후\
DispatcherServlet이 Servlet의 일종으로써, ContextLoaderListener가 IoC Container로써 돌아가는 형태

### Embedded Tomcat

Java Application 위에 Embedded Tomcat이 있고 그 위에 Dispatcher Servlet이 올라가는 형태임

## References

https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html
