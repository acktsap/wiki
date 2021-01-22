package acktsap.webservlet.handlermethod.returns;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import acktsap.webservlet.Event;

/**
 * {@link RequestBody}
 *
 * - 요청 본문(body)에 들어있는 데이터를 HttpMessageConveter를 통해 변환한 객체로받아올 수 있다.
 *
 * - @Valid 또는 @Validated를 사용해서 값을 검증 할 수 있다.
 *
 * - BindingResult 아규먼트를 사용해 코드로 바인딩 또는 검증 에러를 확인할 수 있다.
 *
 *
 * HttpMessageConverter
 *
 * - 스프링 MVC 설정 (WebMvcConfigurer)에서 설정할 수 있다.
 *
 * - configureMessageConverters: 기본 메시지 컨버터 대체
 *
 * - extendMessageConverters: 메시지 컨버터에 추가
 *
 * - 기본 컨버터 : {@link WebMvcConfigurationSupport#addDefaultHttpMessageConverters}, classpath에
 * jackson이 있으면 이거 가져옴
 *
 * HttpEntity
 *
 * - @RequestBody와 비슷하지만 추가적으로 요청 헤더 정보를 사용할 수 있다.
 */
@RestController // ResponseBody를 전부 붙여버려~
@RequestMapping("/requestbody")
public class RequestBodyController {

    @PostMapping
    public Event createEvent(@RequestBody @Valid Event event, BindingResult bindingResult) {
        // save event
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error);
            });
        }
        return event;
    }

    @PostMapping("/httpentity")
    public Event createEvent(HttpEntity<Event> event) {
        // save event
        return event.getBody();
    }

}
