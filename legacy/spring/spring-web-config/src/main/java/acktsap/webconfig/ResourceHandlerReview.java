package acktsap.webconfig;

/**
 *
 * ResourceHandler
 *
 * - 이미지, 자바스크립트, CSS 그리고 HTML 파일과 같은 정적인 리소스를 처리하는 핸들러 등록하는 방법
 *
 * Tomcat 디폴트(Default) 서블릿
 *
 * - 서블릿 컨테이너가 기본으로 제공하는 서블릿으로 정적인 리소스를 처리할 때 사용한다.
 *
 * 스프링 MVC 리소스 핸들러 맵핑 등록
 *
 * - 가장 낮은 우선 순위로 등록. 다른 핸들러 맵핑이 “/” 이하 요청을 처리하도록 허용하고 최종적으로 리소스 핸들러가 처리하도록.
 * {@link DefaultServletHandlerConfigurer}.
 *
 * 리소스 핸들러 설정
 *
 * - 어떤 요청 패턴을 지원할 것인가
 *
 * - 어디서 리소스를 찾을 것인가
 *
 * 캐싱
 *
 * - ResourceResolver: 요청에 해당하는 리소스를 찾는 전략 (eg. 인코딩(gzip, brotli), WebJar, ...)
 *
 * - ResourceTransformer: 응답으로 보낼 리소스를 수정하는 전략 (eg. 캐싱, CSS 링크, HTML5, AppCache, ...)
 *
 * 스프링 부트
 *
 * - 기본 정적 리소스 핸들러와 캐싱 제공, application.properties에서 설정 가능
 */
public class ResourceHandlerReview {

}
