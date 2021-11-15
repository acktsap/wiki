package acktsap.webconfig;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * HTTP 메시지 컨버터
 *
 * - 요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성할 때(@ResponseBody) 형식을 지정할 때 사용한다.
 *
 * 기본 HTTP 메시지 컨버터
 *
 * - 바이트 배열 컨버터
 *
 * - 문자열 컨버터
 *
 * - Resource 컨버터
 *
 * - Form 컨버터 (폼 데이터 to/from MultiValueMap<String, String>)
 *
 * - (JAXB 2 컨버터)
 *
 * - (Jackson 2 컨버터)
 *
 * - (Jackson 컨버터)
 *
 * - (Gson 컨버터)
 *
 * - (Atom 컨버터)
 *
 * - (RSS 컨버터)
 *
 * 설정 방법 (기본)
 *
 * - 기본으로 등록해주는 컨버터에 새로운 컨버터 추가하기: extendMessageConverters
 *
 * - 기본으로 등록해주는 컨버터는 다 무시하고 새로 컨버터 설정하기: configureMessageConverters
 *
 * 추가로 컨버터 등록하기 (추천)
 *
 * - 메이븐 또는 그래들 설정에 의존성을 추가하면 그에 따른 컨버터가 자동으로 등록된다.
 *
 * - {@link WebMvcConfigurationSupport} 에서 설정해줌 (static 보셈)
 *
 * - (이 기능 자체는 스프링 프레임워크의 기능임, 스프링 부트 아님.)
 *
 *
 */
public class MessageConverterReview {

}
