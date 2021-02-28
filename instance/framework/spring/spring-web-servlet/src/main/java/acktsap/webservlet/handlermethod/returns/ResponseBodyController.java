package acktsap.webservlet.handlermethod.returns;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import acktsap.webservlet.Event;

/**
 * {@link ResponseBody}.
 *
 * - 데이터를 HttpMessageConverter를 사용해 응답 본문 메시지로 보낼 때 사용한다.
 *
 * - @RestController (composite annotation )사용시 자동으로 모든 핸들러 메소드에 적용 된다.
 *
 * {@link ResponseEntity}.
 *
 * - 응답 헤더 상태 코드 본문을 직접 다루고 싶은 경우에 사용한다.
 */
@Controller
@RequestMapping("/responsebody")
public class ResponseBodyController {

    @PostMapping
    @ResponseBody
    public Event createEvent1(@RequestBody @Validated(Event.Common.class) Event event) {
        return event;
    }

    @PostMapping("/entity")
    public ResponseEntity<Event> createEvent2(@RequestBody @Validated(Event.Common.class) Event event,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    }

}
