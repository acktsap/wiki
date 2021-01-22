package acktsap.webservlet.handlermethod.arguments;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import acktsap.webservlet.Event;

/**
 * {@link PathVariable}.
 *
 * - 요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법.
 *
 * - 타입 변환 지원.
 *
 * - (기본)값이 반드시 있어야 한다.
 *
 * - Optional 지원.
 *
 * {@link MatrixVariable}.
 *
 * - 요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 방법
 *
 * - 타입 변환 지원.
 *
 * - (기본)값이 반드시 있어야 한다.
 *
 * - Optional 지원.
 *
 * - 이 기능은 기본적으로 비활성화 되어 있음. 활성화 하려면 다음과 같이 설정해야 함.
 */
@Controller
@RequestMapping("/uripattern")
public class UriPatternController {

    @GetMapping("/pathvariable1/{id}")
    @ResponseBody
    public Event pathvariable(@PathVariable Integer id) {
        Event event = new Event();
        event.setId(id);
        return event;
    }

    @GetMapping(value = {"/pathvariable2", "/pathvariable2/{id}"})
    @ResponseBody
    public Event pathvariableWithOptional(@PathVariable Optional<Integer> id) {
        Event event = new Event();
        event.setId(id.orElseGet(() -> Integer.MAX_VALUE));
        return event;
    }

    @GetMapping("/matrixvariable1/{id}")
    @ResponseBody
    public Event matrixVariable(@PathVariable Integer id, @MatrixVariable String name,
                                @MatrixVariable Optional<Integer> limit) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setLimit(limit.orElseGet(() -> Integer.MIN_VALUE));
        return event;
    }

    @GetMapping("/matrixvariable2/{id}")
    @ResponseBody
    public Event matrixVariableWithMap(@PathVariable Integer id,
                                       @MatrixVariable MultiValueMap<String, String> map) {
        Event event = new Event();
        event.setId(id);
        event.setName(map.getFirst("name"));
        return event;
    }

}
