package acktsap.webconfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController : 모든 method에 @ResponstBody를 붙이게 됨
 */
@RestController
public class SampleController {

    // @GetMapping("/hello/{name}") 이거보다 더 좋음 Formatter사용해서 하는게 좋음
    @GetMapping("/hello")
    public String hello(@RequestParam("name") Person person) {
        System.out.println("GET - /hello");
        return "hello " + person.getName();
    }

    /**
     * Jpa 사용하는 경우 Jpa에서 Domain에 해당하는 Converter를 자동으로 등록해 줘서 Formatter같은거 새로 설정 필요 없음.
     */
    @GetMapping("/hello2")
    public String hello2(@RequestParam("id") EntityPerson person) {
        System.out.println("GET - /hello2");
        return "hello " + person.getName();
    }

    /**
     * Uses String converter
     */
    @GetMapping("/message")
    public String message(@RequestBody String body) {
        System.out.println("GET - /message");
        return body;
    }

    /**
     * Uses json converter
     *
     * 스프링 부트를 사용하지 않는 경우
     *
     * - 사용하고 싶은 JSON 라이브러리를 의존성으로 추가
     *
     * - GSON
     *
     * - JacksonJSON
     *
     * - JacksonJSON 2
     *
     * 스프링 부트를 사용하는 경우
     *
     * - 기본적으로 JacksonJSON 2가 의존성에 들어있다.
     */
    @GetMapping("/jsonMessage")
    public EntityPerson jsonMessage(@RequestBody EntityPerson person) {
        System.out.println("GET - /jsonMessage");
        return person;
    }

}
